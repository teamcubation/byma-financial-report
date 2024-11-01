package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.AuthInPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.ApiAuth;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.LoginRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.RegisterRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.TokenResponseDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.AuthMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.util.AnsiColor;
import com.teamcubation.reportservice.util.CurlGenerator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Slf4j
public class AuthController implements ApiAuth {

    private AuthInPort authService;

    public static final String LOGIN_URL = "/api/v1/auth/login";
    public static final String REGISTER_URL = "/api/v1/auth/register";
    public static final String METHOD_POST = "POST";
    public static final String CONTENT_TYPE_JSON = "application/json";

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) throws UserNotFoundException, UserEntityNotFoundException {
        log.info(AnsiColor.BLUE + "Started login request received: {}" + AnsiColor.RESET, CurlGenerator.generateCurl(LOGIN_URL, METHOD_POST, CONTENT_TYPE_JSON, loginRequest));
        User user = AuthMapper.LoginRequestToUser(loginRequest);
        String token = authService.login(user);
        TokenResponseDTO response = TokenResponseDTO.builder().token(token).build();
        log.info(AnsiColor.BLUE + AnsiColor.BLUE + "Finish login response: {}" + AnsiColor.RESET, response);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException {
        log.info(AnsiColor.BLUE + "Started register request received: {}" + AnsiColor.RESET, CurlGenerator.generateCurl(REGISTER_URL, METHOD_POST, CONTENT_TYPE_JSON, registerRequest));
        User newUserFromRequest = AuthMapper.RegisterRequestToUser(registerRequest);
        String token = authService.register(newUserFromRequest);
        TokenResponseDTO response = TokenResponseDTO.builder().token(token).build();
        log.info(AnsiColor.BLUE + "Finished register response token: {}" + AnsiColor.RESET, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
