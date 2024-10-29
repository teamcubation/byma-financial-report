package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;

public interface AuthOutPort {

    String login(User user);

    User register(User user) throws UserEntityNotFoundException, UserNotFoundException;

    User findByEmailIgnoreCase(String email) throws UserEntityNotFoundException, UserNotFoundException;

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUserNameIgnoreCase(String username);
}
