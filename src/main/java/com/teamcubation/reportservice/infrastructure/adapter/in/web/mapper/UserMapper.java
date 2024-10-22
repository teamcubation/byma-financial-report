package com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper;

import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.UserResponse;

public class UserMapper {

    public static User userRequestToUser(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role(UserRole.valueOf(userRequest.getRole()))
                .build();
    }

    public static UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

}
