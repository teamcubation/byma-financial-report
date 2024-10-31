package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserInPort {

    private final UserOutPort userOutPort;
    private final PasswordEncoder passwordEncoder;

    //@CachePut(value = "usersCache", key = "#user.id")
    @Override
    public User create(User user) throws UserDuplicateException, UserNotFoundException, UserEntityNotFoundException {
        if (userOutPort.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserDuplicateException();
        }

        if (userOutPort.existsByNameIgnoreCase(user.getUsername())) {
            throw new UserDuplicateException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userOutPort.registerUser(user);
    }

    @Override
    public User findById(long id) throws UserNotFoundException, UserEntityNotFoundException {
        return userOutPort.findById(id);
    }

    @CachePut(value = "usersCache", key = "#user.id")
    @Override
    public User update(User user) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException {

        User existingUser = userOutPort.findById(user.getId());
        if (user.getEmail() != null) {
            if (!existingUser.getEmail().equals(user.getEmail()) && userOutPort.existsByEmailIgnoreCase(user.getEmail())) {
                throw new UserDuplicateException("Email already exists");
            }
            existingUser.setEmail(user.getEmail());
        }

        if (user.getUsername() != null) {
            if (!existingUser.getUsername().equals(user.getUsername()) && userOutPort.existsByNameIgnoreCase(user.getUsername())) {
                throw new UserDuplicateException("Username already exists");
            }
            existingUser.setUsername(user.getUsername());
        }

        if (user.getRoles() != null) {
            existingUser.setRoles(user.getRoles());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }

        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userOutPort.updateUser(existingUser);
    }

    @CacheEvict(value = "usersCache", key = "#id")
    @Override
    public void delete(long id) throws UserNotFoundException, UserEntityNotFoundException {
        if (userOutPort.findById(id) == null) {
            throw new UserEntityNotFoundException("User not found");
        }
        userOutPort.deleteUserById(id);
    }

    @Cacheable(value = "usersCache")
    @Override
    public List<User> getAll() throws UserNotFoundException {
        return userOutPort.getAll();
    }

}
