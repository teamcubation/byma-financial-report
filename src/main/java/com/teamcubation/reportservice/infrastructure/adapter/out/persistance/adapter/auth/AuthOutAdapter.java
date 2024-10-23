package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.auth;

import com.teamcubation.reportservice.application.port.out.AuthOutPort;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.UserPersistenceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthOutAdapter implements AuthOutPort {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    //TODO modificar los parametros en la interfaz
    @Override
    public String login(User user) {
        return "";
    }

    @Override
    public User register(User user) {

        UserEntity userEntity = UserPersistenceMapper.userToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity saved = userRepository.save(userEntity);
        return UserPersistenceMapper.userEntityToUser(saved);
    }
}
