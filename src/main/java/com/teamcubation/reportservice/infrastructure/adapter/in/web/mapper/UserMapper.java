package com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper;

import com.teamcubation.reportservice.application.service.exception.InvalidUserModel;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserUpdateRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.UserResponse;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.validation.ControllerValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
        log.info("[UserMapper] UserRequest mapped to User: {}", user);

        return user;
    }


    public static UserResponse userToUserResponse(User user) throws InvalidUserModel {
        validateParams(user);
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().name())
                .build();
        log.info("[UserMapper] User mapped to UserResponse: {}", userResponse);
        return userResponse;
    }

    public static User userUpdateRequestDTOToUser(long id, UserUpdateRequestDTO userUpdateRequestDTO) throws InvalidUserModel {
        validateParams(userUpdateRequestDTO);
        User user = User.builder()
                .id(id)
                .username(userUpdateRequestDTO.getUsername())
                .email(userUpdateRequestDTO.getEmail())
                .password(userUpdateRequestDTO.getPassword())
                .roles(UserRole.USER)
                .build();
        log.info("[UserMapper] UserUpdateRequestDTO mapped to User: {}", user);
        return user;
    }

    private static UserRole mapRole(String role) {
        try {
            return UserRole.valueOf(role.toUpperCase());  // Convert to uppercase and map to Enum
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public static List<UserResponse> usersToUserResponses(List<User> users) throws InvalidUserModel {
        validateParams(users);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            log.info("[UserMapper] Mapping user to UserResponse: {}", user);
            userResponses.add(userToUserResponse(user));
        }
        return userResponses;
    }

    private static void validateParams(Object... params) throws InvalidUserModel {
        if (ControllerValidator.isNull(params)) {
            log.error("[UserMapper] Params cannot be null");

            throw new InvalidUserModel("User not found");
        }
    }

}
