package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller;

import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.LoginRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.RegisterRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.TokenResponseDTO;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface ApiAuth {

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<?> login(@Valid LoginRequestDTO loginRequest) throws UserNotFoundException, UserEntityNotFoundException;

    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<TokenResponseDTO> register(@Valid RegisterRequestDTO registerRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException;
}
