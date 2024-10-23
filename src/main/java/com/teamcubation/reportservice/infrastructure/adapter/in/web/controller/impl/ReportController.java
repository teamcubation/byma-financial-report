package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.ReportInPort;
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
    private ReportInPort reportService;

    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(defaultValue = "pdf") String typeFile) throws IOException {
        byte[] fileContent = reportService.generateFile(typeFile);

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

}

