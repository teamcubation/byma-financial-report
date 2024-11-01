package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.RoleOutPort;
import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.exceptionHandler.utils.MessageConstants;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserInPort {

    private final UserOutPort userOutPort;
    private final RoleOutPort roleOutPort;
    private final PasswordEncoder passwordEncoder;


    //@CachePut(value = "usersCache", key = "#user.id")
    @Override
    public User create(User user) throws UserDuplicateException, UserNotFoundException, UserEntityNotFoundException {
        log.info("FCreating user: {}", user);
        if (userOutPort.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserDuplicateException(MessageConstants.DUPLICATED_EMAIL_USER);
        }

        if (userOutPort.existsByNameIgnoreCase(user.getUsername())) {
            throw new UserDuplicateException(MessageConstants.DUPLICATED_USERNAME_USER);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = user.getRoles().stream()
                .map(role -> roleOutPort.findByRole(role.getRole()).orElseGet(() -> roleOutPort.create(role)))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        return userOutPort.registerUser(user);
    }

    @Override
    public User findById(long id) throws UserNotFoundException, UserEntityNotFoundException {
        User user = userOutPort.findById(id);
        log.info("User found by id: {}", user);
        return user;
    }

    @Override
    public User update(User user) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException {
        log.info("Updating user: {}", user);
        User existingUser = userOutPort.findById(user.getId());
        log.info("Existing user: {}", existingUser);
        if (user.getEmail() != null) {
            if (!existingUser.getEmail().equals(user.getEmail()) && userOutPort.existsByEmailIgnoreCase(user.getEmail())) {
                throw new UserDuplicateException(MessageConstants.DUPLICATED_EMAIL_USER);
            }
            log.info("Updating email from {} to {}", existingUser.getEmail(), user.getEmail());
            existingUser.setEmail(user.getEmail());
        }

        if (user.getUsername() != null) {
            if (!existingUser.getUsername().equals(user.getUsername()) && userOutPort.existsByNameIgnoreCase(user.getUsername())) {
                throw new UserDuplicateException(MessageConstants.DUPLICATED_USERNAME_USER);
            }
            log.info("Updating username from {} to {}", existingUser.getUsername(), user.getUsername());
            existingUser.setUsername(user.getUsername());
        }

        if (user.getRoles() != null) {
            log.info("Updating role from {} to {}", existingUser.getRoles(), user.getRoles());

            Set<Role> roles = user.getRoles().stream()
                    .map(role -> roleOutPort.findByRole(role.getRole()).orElseGet(() -> roleOutPort.create(role)))
                    .collect(Collectors.toSet());
            existingUser.setRoles(roles);
        }

        if (user.getPassword() != null) {
            log.info("Updating password from {} to {}", existingUser.getPassword(), user.getPassword());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userOutPort.updateUser(existingUser);
    }

    @Override
    public void delete(long id) throws UserNotFoundException, UserEntityNotFoundException {
        if (userOutPort.findById(id) == null) {
            throw new UserEntityNotFoundException(MessageConstants.USER_ENTITY_NOT_FOUND);
        }
      
        userOutPort.deleteUserById(id);
    }

    @Override
    public List<User> getAll() throws UserNotFoundException {
        log.info("Getting all users");
        return userOutPort.getAll();
    }

}
