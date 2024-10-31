package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.AuthInPort;
import com.teamcubation.reportservice.application.port.out.AuthOutPort;
import com.teamcubation.reportservice.application.service.Jwt.JwtService;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService implements AuthInPort {

    private final AuthOutPort authOutPort;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(User user) throws UserNotFoundException, UserEntityNotFoundException {
        log.info("Login user: {}", user);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                ));

        User userFromDb = authOutPort.findByEmailIgnoreCase(user.getEmail());
        final String token = jwtService.generateToken(userFromDb);
        return token;
    }

    //TODO Implementar reglas de negocio para el registro. Similar al del crud de users
    @Override
    public String register(User user) throws UserDuplicateException, UserNotFoundException, UserEntityNotFoundException {
        log.info("Register user: {}", user);
        if (authOutPort.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserDuplicateException("Email already exists");
        }

        if (authOutPort.existsByUserNameIgnoreCase(user.getUsername())) {
            throw new UserDuplicateException("Username already exists");
        }
        //Hardcodeamos el rol de admin
        if (user.getEmail().equalsIgnoreCase("admin@gmail.com")) {
            user.setRole(UserRole.ADMIN);
        }
        User createdUser = authOutPort.register(user);
        String token = jwtService.generateToken(createdUser);
        return token;
    }
}
