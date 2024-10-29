package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.UserResponse;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
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
public class UserController {

    private final UserInPort userInPort;

    @PostMapping()
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) throws UserDuplicateException, UserNotFoundException {
        User user = UserMapper.userRequestToUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.userToUserResponse(userInPort.create(user)));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll() throws UserNotFoundException {
        return ResponseEntity.ok(UserMapper.usersToUserResponses(userInPort.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(UserMapper.userToUserResponse(userInPort.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws UserNotFoundException, UserEntityNotFoundException {
        userInPort.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable long id, @RequestBody UserRequest userRequest) throws UserDuplicateException, UserNotFoundException {
        User user = UserMapper.userRequestToUser(userRequest);
        return ResponseEntity.ok(UserMapper.userToUserResponse(userInPort.update(id, user)));
    }
}
