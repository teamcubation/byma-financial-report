package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.AuthInPort;
import com.teamcubation.reportservice.application.port.out.AuthOutPort;
import com.teamcubation.reportservice.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements AuthInPort {

    private final AuthOutPort authOutPort;

    @Override
    public String login(User user) {
        return "";
    }

    //TODO Implementar reglas de negocio para el registro. Similar al del crud de users
    @Override
    public User register(User user) {
        User registeredUser = authOutPort.register(user);
        return registeredUser;
    }
}
