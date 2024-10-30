package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.service.exception.InvalidUserModel;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.ApiUser;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserUpdateRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.UserResponse;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.validation.ControllerValidator;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.util.CurlGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController implements ApiUser {

    private final UserInPort userInPort;

    @Override
    @PostMapping()
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException, InvalidUserModel {
        String url = "/api/users";
        String method = "POST";
        String contentType = "application/json";

        log.info("[UserController] User request received in curl format: {}\n", CurlGenerator.generateCurl(url, method, contentType, userRequest));
        UserResponse userResponse = UserMapper.userToUserResponse(userInPort.create(UserMapper.userRequestToUser(null, userRequest)));
        log.info("[UserController] User response created: {}\n", userResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll() throws InvalidUserModel, UserNotFoundException {
        log.info("[UserController] Getting all users");
        return ResponseEntity.ok(UserMapper.usersToUserResponses(userInPort.getAll()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) throws UserNotFoundException, UserEntityNotFoundException, InvalidUserModel {
        log.info("[UserController] Getting user by id: {}", id);
        UserResponse userResponse = UserMapper.userToUserResponse(userInPort.findById(id));
        log.info("[UserController] User found: {}", userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws UserNotFoundException, UserEntityNotFoundException {
        log.info("[UserController] Deleting user by id: {}", id);
        userInPort.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable long id, @RequestBody UserRequest userRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException, InvalidUserModel {
        validateParams(userRequest);
        log.info("[UserController] Updating user by id: {} with data: {}", id, userRequest);
        UserResponse userResponse = UserMapper.userToUserResponse(userInPort.update(UserMapper.userRequestToUser(id, userRequest)));
        log.info("[UserController] User updated: {}", userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("public/{id}")
    public ResponseEntity<UserResponse> updateRegularUser(@PathVariable long id, @RequestBody UserUpdateRequestDTO userRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException, InvalidUserModel {
        validateParams(userRequest);
        log.info("[UserController] Updating user by id: {} with data: {}", id, userRequest);
        User user = UserMapper.userUpdateRequestDTOToUser(id, userRequest);
        log.info("[UserController] User updated: {}", user);
        return ResponseEntity.ok(UserMapper.userToUserResponse(userInPort.update(user)));
    }

    private void validateParams(Object... params) throws InvalidUserModel {
        if (ControllerValidator.isNull(params)) {
            log.error("Params cannot be null");
            throw new InvalidUserModel("User not found");
        }

    }


}
