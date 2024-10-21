package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.domain.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserOutPort {

    Optional<User> findById(long id);
    User save(User user);
    void deleteById(long id);
    boolean existsByEmailIgnoreCase(String email);
    List<User> getAll();



}
