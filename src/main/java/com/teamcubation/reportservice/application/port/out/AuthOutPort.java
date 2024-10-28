package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;

public interface AuthOutPort {

    String login(User user);

    User register(User user) throws UserNotFoundException;

    User findByEmailIgnoreCase(String email) throws Exception;


    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUserNameIgnoreCase(String username);
}
