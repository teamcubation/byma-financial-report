package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.AuthInPort;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.LoginRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.RegisterRequestDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthInPort authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        return null;
    }
}
