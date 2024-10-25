package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.domain.model.user.User;

import java.util.List;

public interface UserInPort {

    User create(User user);

    User findById(long id) throws Exception;

    User update(long id, User user) throws UserDuplicateException;

    void delete(long id) throws Exception;

    List<User> getAll();

}
