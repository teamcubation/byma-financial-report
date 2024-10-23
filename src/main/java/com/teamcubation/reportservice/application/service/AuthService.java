package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.AuthInPort;
import com.teamcubation.reportservice.domain.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthInPort {
    @Override
    public String login(User user) {
        return "";
    }

    //TODO Implementar reglas de negocio para el registro. Similar al del crud de users
    @Override
    public User register(User user) {
        return null;
    }
}
