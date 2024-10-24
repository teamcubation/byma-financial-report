package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserInPort {

    private final UserOutPort userOutPort;

    @Override
    public User create(User user) throws UserDuplicateException {
        if (userOutPort.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserDuplicateException();
        }

        if (userOutPort.existsByNameIgnoreCase(user.getUsername())) {
            throw new UserDuplicateException();
        }

        return userOutPort.registerUser(user);
    }

    @Override
    public User findById(long id) throws Exception {
        return userOutPort.findById(id);
    }

    @Override
    public User update(long id, User user) throws Exception {

        User existingUser = userOutPort.findById(id);
        if (user.getEmail() != null) {
            if (!existingUser.getEmail().equals(user.getEmail()) && userOutPort.existsByEmailIgnoreCase(user.getEmail())) {
                throw new UserDuplicateException();
            }
            existingUser.setEmail(user.getEmail());
        }

        if (user.getUsername() != null) {
            if (!existingUser.getUsername().equals(user.getUsername()) && userOutPort.existsByNameIgnoreCase(user.getUsername())) {
                throw new UserDuplicateException();
            }
            existingUser.setUsername(user.getUsername());
        }

        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }

        return userOutPort.updateUser(existingUser);
    }

    @Override
    public void delete(long id) throws Exception {
        if (userOutPort.findById(id) == null) {
            throw new UserNotFoundException();
        }
        userOutPort.deleteUserById(id);
    }

    @Override
    public List<User> getAll() {
        return userOutPort.getAll();
    }

}
