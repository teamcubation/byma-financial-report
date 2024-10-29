package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.ReportInPort;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.ApiReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(("/report"))
public class ReportController implements ApiReport {
    @Autowired
    private ReportInPort reportService;

    @Override
    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(defaultValue = "pdf") String typeFile, @RequestParam(required = false) String typeInstrument) throws IOException {
        byte[] fileContent = reportService.generateFile(typeFile, typeInstrument);

        return ResponseEntity.ok()
                .headers(this.generateHeader("application/" + typeFile, "report." + typeFile))
                .body(fileContent);
    }

    private HttpHeaders generateHeader(String contentType, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        return headers;
    }

    @GetMapping("/reportHistory")
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/reportHistoryByEmail")
    public ResponseEntity<List<Report>> getReportsByEmail() {
        List<Report> reports = reportService.findByUserEmail();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/downloadReport/{id}")
    public ResponseEntity<byte[]> downloadExistingReport(@PathVariable String id, @RequestParam String typeFile) throws IOException {
        byte[] fileContent = reportService.downloadFile(id);

        return ResponseEntity.ok()
                .headers(this.generateHeader("application/" + typeFile, "report." + typeFile))
                .body(fileContent);
    }
}