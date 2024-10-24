package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.ReportInPort;
import com.teamcubation.reportservice.domain.model.report.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(("/report"))
public class ReportController {
    @Autowired
    private ReportInPort reportService;

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
    public ResponseEntity<List<Report>> getAllReports(){
        return ResponseEntity.ok(reportService.getAllReports());
    }
}