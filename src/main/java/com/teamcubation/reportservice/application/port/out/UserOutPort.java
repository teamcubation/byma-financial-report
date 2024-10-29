package com.teamcubation.reportservice.application.port.out;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;

import java.util.List;

public interface UserOutPort {

    User registerUser(User user) throws UserDuplicateException;

    User findByEmailIgnoreCase(String email) throws UserNotFoundException;

    User findByUsername(String username) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;

    List<User> getAll() throws UserNotFoundException;

    User updateUser(User user) throws UserNotFoundException;

    void deleteUserById(Long id) throws UserEntityNotFoundException;

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByNameIgnoreCase(String name);


}
