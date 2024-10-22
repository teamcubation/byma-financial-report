package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(("/report"))
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping()
    public ResponseEntity<?> testConnectionBond() {

        log.info("testConnectionBond");
        return ResponseEntity.ok().body(reportService.getAllBonds());
    }

    @GetMapping("/stocks")
    public ResponseEntity<?> testConnectionStock() {
        log.info("testConnectionStock");
        return ResponseEntity.ok().body(reportService.getAllStocks());
    }
}
