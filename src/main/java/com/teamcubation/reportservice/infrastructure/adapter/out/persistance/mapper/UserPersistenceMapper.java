package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper;

import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.validation.PersistanceValidation;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.role.RoleEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserPersistenceMapper {

    public static User userEntityToUser(UserEntity userEntity) throws UserNotFoundException {
        if (PersistanceValidation.isNull(userEntity)) {
            throw new UserNotFoundException();
        }

        User user = User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(userEntity.getRoles().stream().map(role -> Role.builder().id(role.getId()).role(role.getRole()).build()).collect(Collectors.toSet()))
                .build();
        log.info("UserEntity mapped to User: {}", user);
        return user;
    }

    public static UserEntity userToUserEntity(User user) throws UserEntityNotFoundException {
        if (PersistanceValidation.isNull(user)) {
            throw new UserEntityNotFoundException("User entity cannot be null");
        }
        log.info("Mapping user to UserEntity: {}", user);
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(role -> RoleEntity.builder().id(role.getId()).role(role.getRole()).build()).collect(Collectors.toSet()))
                .build();
        log.info("User mapped to UserEntity: {}", userEntity);
        return userEntity;
    }

    public static List<User> userEntitiesToUsers(List<UserEntity> userEntities) throws UserEntityNotFoundException, UserNotFoundException {
        List<User> users = new ArrayList<>();
        if (userEntities == null) {
            throw new UserEntityNotFoundException("User entities cannot be null");
        }
        for (UserEntity userEntity : userEntities) {
            users.add(userEntityToUser(userEntity));
        }
        return users;
    }

}
