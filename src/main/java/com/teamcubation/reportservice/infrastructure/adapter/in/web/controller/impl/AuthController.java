package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.AuthInPort;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.LoginRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.RegisterRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.TokenResponseDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.AuthMapper;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthInPort authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {

        User newUserFromRequest = AuthMapper.RegisterRequestToUser(registerRequest);
        String token = authService.register(newUserFromRequest);
        TokenResponseDTO response = TokenResponseDTO.builder().token(token).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
