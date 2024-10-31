package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.ReportInPort;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.ApiReport;
import com.teamcubation.reportservice.util.AnsiColor;
import com.teamcubation.reportservice.util.CurlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(("/report"))
public class ReportController implements ApiReport {
    @Autowired
    private ReportInPort reportService;

    private static final String URL = "/report/generateReport";
    private static final String METHOD = "GET";
    private static final String CONTENT_TYPE = "application/json";


    @Override
    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(defaultValue = "pdf") String typeFile, @RequestParam(required = false) String typeInstrument) throws IOException {

        Map<String, Object> params = new HashMap<>();
        params.put("typeFile", typeFile);
        params.put("typeInstrument", typeInstrument);

        log.info(AnsiColor.BLUE + "Started report request received in curl format: {}\n" + AnsiColor.RESET, CurlGenerator.generateCurl(URL, METHOD, CONTENT_TYPE, params));
        byte[] fileContent = reportService.generateFile(typeFile, typeInstrument);
        HttpHeaders headers = this.generateHeader("application/" + typeFile, "report." + typeFile);
        log.info(AnsiColor.BLUE + "Finished: file generated successfully" + AnsiColor.RESET);
        return ResponseEntity.ok()
                .headers(headers)
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
        log.info(AnsiColor.BLUE + "Started getting all reports" + AnsiColor.RESET);
        List<Report> reports = reportService.getAllReports();
        log.info(AnsiColor.BLUE + "Finished reports found" + AnsiColor.RESET);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reportHistoryByEmail")
    public ResponseEntity<List<Report>> getReportsByEmail() {
        log.info(AnsiColor.BLUE + "Started getting reports by email" + AnsiColor.RESET);
        List<Report> reports = reportService.findByUserEmail();
        log.info(AnsiColor.BLUE + "Finished reports found finished" + AnsiColor.RESET);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/downloadReport/{id}")
    public ResponseEntity<byte[]> downloadExistingReport(@PathVariable String id, @RequestParam String typeFile) throws IOException {
        log.info(AnsiColor.BLUE + "Started downloading report with id: {} and typeFile: {}" + AnsiColor.RESET, id, typeFile);
        byte[] fileContent = reportService.downloadFile(id);
        log.info(AnsiColor.BLUE + "Finished: file downloaded successfully" + AnsiColor.RESET);
        return ResponseEntity.ok()
                .headers(this.generateHeader("application/" + typeFile, "report." + typeFile))
                .body(fileContent);
    }
}