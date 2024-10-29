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
        User user = UserMapper.userRequestToUser(null, userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.userToUserResponse(userInPort.create(user)));
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll() throws InvalidUserModel, UserNotFoundException {
        return ResponseEntity.ok(UserMapper.usersToUserResponses(userInPort.getAll()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) throws UserNotFoundException, UserEntityNotFoundException, InvalidUserModel {
        return ResponseEntity.ok(UserMapper.userToUserResponse(userInPort.findById(id)));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws UserNotFoundException, UserEntityNotFoundException {
        userInPort.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable long id, @RequestBody UserRequest userRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException, InvalidUserModel {
        validateParams(userRequest);
        User user = UserMapper.userRequestToUser(id ,userRequest);
        return ResponseEntity.ok(UserMapper.userToUserResponse(userInPort.update(user)));
    }

    @PutMapping("public/{id}")
    public ResponseEntity<UserResponse> updateRegularUser(@PathVariable long id, @RequestBody UserUpdateRequestDTO userRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException, InvalidUserModel {
        validateParams(userRequest);
        User user = UserMapper.userUpdateRequestDTOToUser(id, userRequest);
        return ResponseEntity.ok(UserMapper.userToUserResponse(userInPort.update(user)));
    }

    private void validateParams(Object ...params) throws InvalidUserModel {
            if (ControllerValidator.isNull(params)) {
                log.error("Params cannot be null");
                throw new InvalidUserModel("User not found");
            }
    }
}
