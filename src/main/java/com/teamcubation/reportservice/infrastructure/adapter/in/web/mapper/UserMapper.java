package com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper;

import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User userRequestToUser(UserRequest userRequest) {
        if (userRequest == null) {
            throw new UserNotFoundException();
        }

        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        if (userRequest.getRole() != null) {
            user.setRole(mapRole(userRequest.getRole()));
        }

        return user;
    }


    public static UserResponse userToUserResponse(User user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();
    }

    private static UserRole mapRole(String role) {
        try {
            return UserRole.valueOf(role.toUpperCase());  // Convert to uppercase and map to Enum
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public static List<UserResponse> usersToUserResponses(List<User> users) {
        if (users == null) {
            throw new UserNotFoundException();
        }
        return users.stream()
                .map(UserMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

}
