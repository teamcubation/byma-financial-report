package com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper;

import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.LoginRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.RegisterRequestDTO;

public class AuthMapper {

    public static User RegisterRequestToUser(RegisterRequestDTO registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(UserRole.USER)
                .build();
    }

    public static User LoginRequestToUser(LoginRequestDTO loginRequest) {
        return User.builder()
                .email(loginRequest.getEmail())
                .password(loginRequest.getPassword())
                .build();
    }
}
