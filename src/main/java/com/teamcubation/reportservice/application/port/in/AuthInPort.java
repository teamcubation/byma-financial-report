package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;

public interface AuthInPort {

    String login(User user) throws UserNotFoundException, UserEntityNotFoundException;

    String register(User user) throws UserDuplicateException, UserNotFoundException, UserEntityNotFoundException;
}
