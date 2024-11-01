package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.auth;

import com.teamcubation.reportservice.application.port.out.AuthOutPort;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.exceptionHandler.utils.MessageConstants;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.UserPersistenceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthOutAdapter implements AuthOutPort {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //TODO modificar los parametros en la interfaz
    @Override
    public String login(User user) {
        return "";
    }

    @Override
    public User register(User user) throws UserEntityNotFoundException, UserNotFoundException {
        log.info("Register user: {}", user);
        UserEntity userEntity = UserPersistenceMapper.userToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity saved = userRepository.save(userEntity);
        log.info("User saved: {}", saved);
        return UserPersistenceMapper.userEntityToUser(saved);
    }

    @Override
    public User findByEmailIgnoreCase(String email) throws UserEntityNotFoundException, UserNotFoundException {
        return UserPersistenceMapper.userEntityToUser(userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserEntityNotFoundException(MessageConstants.USER_ENTITY_NOT_FOUND)));
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        log.info("Check if user exists by email: {}", email);
        return userRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByUserNameIgnoreCase(String username) {
        log.info("Check if user exists by username: {}", username);
        return userRepository.existsByUsernameIgnoreCase(username);
    }
}
