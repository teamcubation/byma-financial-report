package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
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
        return userOutPort.save(user);
    }

    @Override
    public User findById(long id) throws UserNotFoundException {
        return userOutPort.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User update(long id, User user) throws UserNotFoundException, UserDuplicateException {

        User existingUser = userOutPort.findById(id).orElseThrow(UserNotFoundException::new);
        if (user.getEmail() != null) {
            if (!existingUser.getEmail().equals(user.getEmail()) && userOutPort.existsByEmailIgnoreCase(user.getEmail())) {
                throw new UserDuplicateException();
            }
            existingUser.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            if (!existingUser.getName().equals(user.getName()) && userOutPort.existsByNameIgnoreCase(user.getName())) {
                throw new UserDuplicateException();
            }
            existingUser.setName(user.getName());
        }

        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }

        return userOutPort.save(user);
    }

    @Override
    public void delete(long id) throws UserNotFoundException {
        if (userOutPort.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userOutPort.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userOutPort.getAll();
    }


}
