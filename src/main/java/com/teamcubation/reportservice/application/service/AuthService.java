package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.AuthInPort;
import com.teamcubation.reportservice.application.port.out.AuthOutPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements AuthInPort {

    private final AuthOutPort authOutPort;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(User user) throws Exception {
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
    public String register(User user) {

        if (authOutPort.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserDuplicateException("Email already exists");
        }

        if (authOutPort.existsByUserNameIgnoreCase(user.getUsername())) {
            throw new UserDuplicateException("Username already exists");
        }
        User createdUser = authOutPort.register(user);
        String token = jwtService.generateToken(createdUser);
        return token;
    }
}
