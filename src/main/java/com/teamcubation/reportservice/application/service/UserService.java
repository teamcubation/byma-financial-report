package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserInPort {

    private final UserOutPort userOutPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) throws UserDuplicateException, UserNotFoundException, UserEntityNotFoundException {
        log.info("FCreating user: {}", user);
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
        User user = userOutPort.findById(id);
        log.info("User found by id: {}", user);
        return user;
    }

    @CachePut(value = "usersCache", key = "#user.id")
    @Override
    public User update(User user) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException {
        log.info("Updating user: {}", user);
        User existingUser = userOutPort.findById(user.getId());
        log.info("Existing user: {}", existingUser);
        if (user.getEmail() != null) {
            if (!existingUser.getEmail().equals(user.getEmail()) && userOutPort.existsByEmailIgnoreCase(user.getEmail())) {
                throw new UserDuplicateException("Email already exists");
            }
            log.info("Updating email from {} to {}", existingUser.getEmail(), user.getEmail());
            existingUser.setEmail(user.getEmail());
        }

        if (user.getUsername() != null) {
            if (!existingUser.getUsername().equals(user.getUsername()) && userOutPort.existsByNameIgnoreCase(user.getUsername())) {
                throw new UserDuplicateException("Username already exists");
            }
            log.info("Updating username from {} to {}", existingUser.getUsername(), user.getUsername());
            existingUser.setUsername(user.getUsername());
        }

        if (user.getRoles() != null) {
            log.info("Updating role from {} to {}", existingUser.getRoles(), user.getRoles());
            existingUser.setRoles(user.getRoles());
        }

        if (user.getPassword() != null) {
            log.info("Updating password from {} to {}", existingUser.getPassword(), user.getPassword());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

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

    @Override
    public List<User> getAll() throws UserNotFoundException {
        log.info("Getting all users");
        return userOutPort.getAll();
    }

}
