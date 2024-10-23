package com.teamcubation.reportservice.application.service;
import com.teamcubation.reportservice.application.port.in.ReportInPort;
import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;

import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.application.service.generatorfile.GeneratorCsv;
import com.teamcubation.reportservice.application.service.generatorfile.GeneratorPdf;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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

        reportOutPort.save(createReport(fileContent));
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

        return GeneratorPdf.generatePdfContent(allBonds, allStocks);
    }

    public byte[] generateCsv(String typeInstrument) throws IOException {
        List<BonoDto> allBonds = connectionOutPort.getAllBonds();
        List<StockDto> allStocks = connectionOutPort.getAllStocks();

        return GeneratorCsv.generateCsv(allBonds, allStocks);
    }

    public List<Report> getAllReports() {
        return reportOutPort.getAll();
    }
}

