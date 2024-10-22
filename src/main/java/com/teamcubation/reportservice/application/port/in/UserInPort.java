package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.domain.model.user.User;

import java.util.List;

public interface UserInPort {

    User create(User user);

    User findById(long id);

    User update(long id, User user);

    void delete(long id);

    List<User> getAll();

}
