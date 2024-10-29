package com.teamcubation.reportservice.application.service;
import com.teamcubation.reportservice.application.port.in.ReportInPort;
import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;

import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.application.service.generatorfile.GeneratorCsv;
import com.teamcubation.reportservice.application.service.generatorfile.GeneratorPdf;
import com.teamcubation.reportservice.domain.customexceptions.report.InvalidInstrumentException;
import com.teamcubation.reportservice.domain.customexceptions.report.InvalidTypeFyleException;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.ReportPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService implements ReportInPort {
    private final ConnectionOutPort connectionOutPort;
    private final ReportOutPort reportOutPort;
    private final UserOutPort userOutPort;

    private static final String CSV_TYPE = "csv";
    private static final String PDF_TYPE = "pdf";
    private static final String BONDS_TYPE = "bonds";
    private static final String STOCKS_TYPE = "stocks";
    private static final String BASE_URL = "/report/downloadReport";
    private static final String FILE_TYPE_NOT_SUPPORTED = "File type not supported";
    private static final String USER_NOT_AUTHENTICATED = "User not authenticated";
    private static final String INSTRUMENT_TYPE_NOT_SUPPORTED = "Instrument type not supported";
    private static final String REPORT_NOT_FOUND = "Report not found";

    public byte[] generateFile(String typeFile, String typeInstrument) throws IOException {
        log.info("Generating file: typeFile={}, typeInstrument={}", typeFile, typeInstrument);
        byte[] fileContent = switch (typeFile) {
            case CSV_TYPE -> generateCsv(typeInstrument);
            case PDF_TYPE -> generatePdf(typeInstrument);
            default -> throw new InvalidTypeFyleException(FILE_TYPE_NOT_SUPPORTED);
        };
        String userEmail = getAuthenticatedUserEmail();
        save(createReport(fileContent, userEmail, typeFile, typeInstrument));
        log.info("File generated successfully: typeFile={}, typeInstrument={}", typeFile, typeInstrument);
        return fileContent;
    }

    private String getAuthenticatedUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            log.info("Authenticated user email: {}", authentication.getName());
            return authentication.getName();
        }
        log.warn("No authenticated user found");
        throw new RuntimeException(USER_NOT_AUTHENTICATED);
    }

    private Report createReport(byte[] reportContent, String userEmail, String typeFile, String typeInstrument) {
        log.info("Creating report for userEmail={}, typeFile={}, typeInstrument={}", userEmail, typeFile, typeInstrument);
        List<String> downloadUrls = new ArrayList<>();
        Report report = Report.builder()
                .userEmail(userEmail)
                .title("Report " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .content(reportContent)
                .creationDate(LocalDateTime.now())
                .build();
        Report savedReport = save(report);

        if (typeInstrument == null) {
            downloadUrls.add(BASE_URL + "/" + savedReport.getId() + "?typeFile=" + typeFile);
        } else {
            downloadUrls.add(BASE_URL + "/" + savedReport.getId() + "?typeFile=" + typeFile + "&typeInstrument=" + typeInstrument);
        }
        savedReport.setDownloadUrl(downloadUrls);
        log.info("Report created successfully with ID: {}", savedReport.getId());
        return save(savedReport);

    }

    public byte[] downloadFile(String id) throws IOException {
        ReportEntity reportEntity = reportOutPort.findById(id);
        if (reportEntity == null) {
            log.warn(REPORT_NOT_FOUND);
            throw new RuntimeException(REPORT_NOT_FOUND);
        }
        return reportEntity.getContent();
    }

    public byte[] generatePdf(String typeInstrument) throws IOException {
        log.info("Fetching all bonds and stocks for PDF generation.");
        if (typeInstrument == null) {
            return GeneratorPdf.generatePdfContent(connectionOutPort.getAllBonds(), connectionOutPort.getAllStocks());
        } 
        if (typeInstrument.equals(BONDS_TYPE)) {
            return GeneratorPdf.generatePdfContent(connectionOutPort.getAllBonds(), null);
        }
        if (typeInstrument.equals(STOCKS_TYPE)) {
            return GeneratorPdf.generatePdfContent(null, connectionOutPort.getAllStocks());
        }
        log.warn(INSTRUMENT_TYPE_NOT_SUPPORTED);
        throw new InvalidInstrumentException(INSTRUMENT_TYPE_NOT_SUPPORTED);
    }

   

    public byte[] generateCsv(String typeInstrument) throws IOException {
        log.info("Fetching all stocks and stocks for PDF generation.");
        if (typeInstrument == null) {
            return GeneratorCsv.generateCsv(connectionOutPort.getAllBonds(), connectionOutPort.getAllStocks());
        } 
        if (typeInstrument.equals(BONDS_TYPE)) {
            return GeneratorCsv.generateCsv(connectionOutPort.getAllBonds(), null);
        }
        if (typeInstrument.equals(STOCKS_TYPE)) {
            return GeneratorCsv.generateCsv(null, connectionOutPort.getAllStocks());
        }
        log.warn(INSTRUMENT_TYPE_NOT_SUPPORTED);
        throw new InvalidInstrumentException(INSTRUMENT_TYPE_NOT_SUPPORTED);
    }

    public List<Report> findByUserEmail() {
        String userEmail = getAuthenticatedUserEmail();
        return getReportsByUserEmail(userEmail);
    }

    @Cacheable(value = "reportsCache", key = "#userEmail")
    public List<Report> getReportsByUserEmail(String userEmail) {
        log.info("Fetching reports from cache for user: {}", userEmail);
        List<Report> reports = reportOutPort.findByUserEmail(userEmail).stream()
                .map(ReportPersistenceMapper::reportEntityToReportModel)
                .collect(Collectors.toList());
        log.info("Reports fetched for user {}: {}", userEmail, reports.size());
        return reports;
    }

    @Cacheable(value = "allReportsCache")
    public List<Report> getAllReports() {
        return reportOutPort.getAll().stream()
                .map(ReportPersistenceMapper::reportEntityToReportModel)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "reportsCache", key = "#userEmail")
    public Report save(Report report) {
        String userEmail = getAuthenticatedUserEmail();
        return ReportPersistenceMapper.reportEntityToReportModel(reportOutPort.save(report));
    }
}