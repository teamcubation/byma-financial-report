package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller;

import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.exception.reportException.InvalidObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ApiReport {

    @Operation(summary = "Generate and download report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report generated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<byte[]> downloadFile(String typeFile, String typeInstrument) throws IOException, InvalidObject;

    @Operation(summary = "Get all reports history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<Report>> getAllReports() throws InvalidObject;
}
