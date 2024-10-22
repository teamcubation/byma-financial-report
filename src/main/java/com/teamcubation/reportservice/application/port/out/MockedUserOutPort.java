package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;

import java.util.List;

public interface MockedUserOutPort {


    User registerUser(User user);

    User findByEmail(String email) throws Exception;

    User findByUsername(String username) throws Exception;

    User findById(Long id) throws Exception;

    List<User> getAll();

    User updateUser(User user);

    void deleteUser(Long id) throws UserEntityNotFoundException;



}
