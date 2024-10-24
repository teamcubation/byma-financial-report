package com.teamcubation.reportservice.application.service;
import com.teamcubation.reportservice.application.port.in.ReportInPort;
import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;

import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.application.service.generatorfile.GeneratorCsv;
import com.teamcubation.reportservice.application.service.generatorfile.GeneratorPdf;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.ReportPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService implements ReportInPort {
    private final ConnectionOutPort connectionOutPort;

    private final ReportOutPort reportOutPort;

    public byte[] generateFile(String typeFile, String typeInstrument) throws IOException {
        byte[] fileContent;
        String downloadUrl;
        if (typeFile.equals("csv")) {
            fileContent = generateCsv(typeInstrument);
            downloadUrl = "/reports/download/csv";
        } else if (typeFile.equals("pdf")) {
            fileContent = generatePdf(typeInstrument);
            downloadUrl = "/reports/download/pdf";
        } else
            throw new IllegalArgumentException("File type not supported");

        save(createReport(fileContent));
        return fileContent;
    }
    
    private Report createReport(byte[] reportContent) {
        return Report.builder()
                .id("2")
                .userEmail("user123")
                .title("report1")
                .downloadUrlPdf("urlpdf")
                .downloadUrlCsv("urlcsv")
                .content(reportContent)
                .creationDate(LocalDateTime.now())
                .build();
    }

    public byte[] generatePdf(String typeInstrument) throws IOException {
        List<BonoDto> allBonds = connectionOutPort.getAllBonds();
        List<StockDto> allStocks = connectionOutPort.getAllStocks();
        if (typeInstrument == null) {
            return GeneratorPdf.generatePdfContent(allBonds, allStocks);
        }
        if (typeInstrument.equals("bonds")) {
            return GeneratorPdf.generatePdfContent(allBonds, null);
        }
        if (typeInstrument.equals("stocks")) {
            return GeneratorPdf.generatePdfContent(null, allStocks);
        }
        throw new IllegalArgumentException("Instrument type not supported");

    }

    public byte[] generateCsv(String typeInstrument) throws IOException {
        List<BonoDto> allBonds = connectionOutPort.getAllBonds();
        List<StockDto> allStocks = connectionOutPort.getAllStocks();
        if (typeInstrument == null) {
            return GeneratorCsv.generateCsv(allBonds, allStocks);
        }
        if (typeInstrument.equals("bonds")) {
            return GeneratorCsv.generateCsv(allBonds, null);
        }
        if (typeInstrument.equals("stocks")) {
            return GeneratorCsv.generateCsv(null, allStocks);
        }
        throw new IllegalArgumentException("Instrument type not supported");
    }

    @Cacheable(value = "reportsCache", key = "#email")
    public List<Report> findByUserEmail(String email) {
        return reportOutPort.findByUserEmail(email).stream()
                .map(ReportPersistenceMapper::reportEntityToReportModel)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "reportsCache")
    public List<Report> getAllReports() {
        return reportOutPort.getAll().stream()
                .map(ReportPersistenceMapper::reportEntityToReportModel)
                .collect(Collectors.toList());
    }

    @CachePut(value = "reportsCache", key = "#report.userEmail")
    public Report save(Report report) {
        return ReportPersistenceMapper.reportEntityToReportModel(reportOutPort.save(report));
    }
}