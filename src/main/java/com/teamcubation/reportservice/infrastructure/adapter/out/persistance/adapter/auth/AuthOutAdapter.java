package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.auth;

import com.teamcubation.reportservice.application.port.out.AuthOutPort;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AuthOutAdapter implements AuthOutPort {

    private final UserRepository userRepository;

    //TODO modificar los parametros en la interfaz
    @Override
    public String login(User user) {
        return "";
    }

    @Override
    public User register(User user) {
        return null;
    }
}
