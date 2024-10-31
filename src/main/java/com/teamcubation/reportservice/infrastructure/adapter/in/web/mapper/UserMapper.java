package com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper;

import com.teamcubation.reportservice.application.service.exception.InvalidUserModel;
import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserUpdateRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.UserResponse;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.validation.ControllerValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class UserMapper {

    public static User userRequestToUser(Long id, UserRequest userRequest) throws InvalidUserModel {
        validateParams(userRequest);
        User user = User.builder()
                .id(id)
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        if (userRequest.getRoles() != null) {
            user.setRoles(mapRole(userRequest.getRoles()));
        }

        return user;
    }


    public static UserResponse userToUserResponse(User user) throws InvalidUserModel {
        validateParams(user);
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(mapRolesToResponse(user.getRoles()))
                .build();
    }

    public static User userUpdateRequestDTOToUser(long id, UserUpdateRequestDTO userUpdateRequestDTO) throws InvalidUserModel {
        validateParams(userUpdateRequestDTO);
        return User.builder()
                .id(id)
                .username(userUpdateRequestDTO.getUsername())
                .email(userUpdateRequestDTO.getEmail())
                .password(userUpdateRequestDTO.getPassword())
                .roles(Set.of(Role.builder().role(UserRole.USER).build())) //Role USER by default on this public user update endpoint
                .build();
    }

    private static Set<Role> mapRole(Set<String> roles) {
        try {
            return roles.stream()
                    .map(role -> Role.builder().role(UserRole.valueOf(role.toUpperCase())).build())
                    .collect(Collectors.toSet());  // Convert to uppercase and map to Enum
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid roles: " + roles);
        }
    }

    private static Set<String> mapRolesToResponse(Set<Role> roles) {
        try {
            return roles.stream()
                    .map(role -> role.getRole().name())
                    .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid roles: " + roles);
        }
    }

    public static List<UserResponse> usersToUserResponses(List<User> users) throws InvalidUserModel {
        validateParams(users);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(userToUserResponse(user));
        }
        return userResponses;
    }

    private static void validateParams(Object... params) throws InvalidUserModel {
        if (ControllerValidator.isNull(params)) {
            throw new InvalidUserModel("User not found");
        }
    }

}