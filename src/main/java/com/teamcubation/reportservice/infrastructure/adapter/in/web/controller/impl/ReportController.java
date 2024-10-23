package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(("/report"))
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/generatepdf")
    public ResponseEntity<byte[]> downloadFilePdf() throws IOException {
        byte[] fileContent = reportService.generatePdf();

        return ResponseEntity.ok()
                .headers(this.generateHeader("application/pdf", "report.pdf"))
                .body(fileContent);
    }

    @GetMapping("/download-file")
    public ResponseEntity<byte[]> downloadFileCsv() throws IOException {
        byte[] fileContent = reportService.generateCsv();

        return ResponseEntity.ok()
                .headers(this.generateHeader("application/csv", "report.csv"))
                .body(fileContent);
    }

    private HttpHeaders generateHeader(String contentType, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        return headers;
    }

}

