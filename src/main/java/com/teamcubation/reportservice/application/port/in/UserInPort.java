package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;

import java.util.List;

public interface UserInPort {

    User create(User user) throws UserDuplicateException, UserNotFoundException, UserEntityNotFoundException;

    User findById(long id) throws UserNotFoundException, UserEntityNotFoundException;

    User update(User user) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException;

    void delete(long id) throws UserNotFoundException, UserEntityNotFoundException;

    List<User> getAll();

}
