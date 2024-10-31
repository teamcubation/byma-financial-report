package com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper;

import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.LoginRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.RegisterRequestDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class AuthMapper {

    public static User RegisterRequestToUser(RegisterRequestDTO registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .roles(Set.of(Role.builder().role(UserRole.USER).build())) //Role USER by default
                .build();
        log.info("User: {}", user);
        return user;
    }

    public static User LoginRequestToUser(LoginRequestDTO loginRequest) {
        User user = User.builder()
                .email(loginRequest.getEmail())
                .password(loginRequest.getPassword())
                .build();
        log.info("User: {}", user);
        return user;
    }
}
