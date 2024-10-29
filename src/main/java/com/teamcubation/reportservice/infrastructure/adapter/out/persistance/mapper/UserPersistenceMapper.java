package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper;

import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.validation.PersistanceValidation;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserPersistenceMapper {

    public static User userEntityToUser(UserEntity userEntity) throws UserNotFoundException {
        if (PersistanceValidation.isNull(userEntity)) {
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

    public static UserEntity userToUserEntity(User user) throws UserEntityNotFoundException {
        if (PersistanceValidation.isNull(user)) {
            throw new UserEntityNotFoundException("User entity cannot be null");
        }
      
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static List<User> userEntitiesToUsers(List<UserEntity> userEntities) throws UserEntityNotFoundException, UserNotFoundException {
        List<User> users = new ArrayList<>();
      
        if (userEntities == null) {
            throw new UserEntityNotFoundException("User entities cannot be null");
        }
      
        for(UserEntity userEntity : userEntities) {
            users.add(userEntityToUser(userEntity));
        }
      
        return users;
    }

}
