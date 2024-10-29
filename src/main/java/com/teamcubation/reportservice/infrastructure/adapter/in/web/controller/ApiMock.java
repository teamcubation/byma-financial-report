package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ApiMock {

    @Operation(summary = "Get a mock response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mock response retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    String mock();

    @Operation(summary = "Get user restricted response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User response retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    String user();

    @Operation(summary = "Get admin restricted response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin response retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    String admin();

    @Operation(summary = "Get authenticated user response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated user response retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public String authenticated() throws Exception;
}
