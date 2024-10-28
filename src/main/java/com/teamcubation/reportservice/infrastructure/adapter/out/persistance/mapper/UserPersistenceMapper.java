package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper;

import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserPersistenceMapper {

    public static User userEntityToUser(UserEntity userEntity) throws UserNotFoundException {
        if (userEntity == null) {
            throw new UserNotFoundException();
        }
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(UserRole.valueOf(userEntity.getRole().toString()))
                .build();
    }

    public static UserEntity userToUserEntity(User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException();
        }
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static List<User> userEntitiesToUsers(List<UserEntity> userEntities) throws UserNotFoundException {
        if (userEntities == null) {
            throw new UserNotFoundException();
        }

        List<User> users = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            users.add(UserPersistenceMapper.userEntityToUser(userEntity));
        }

        return users;
    }

}
