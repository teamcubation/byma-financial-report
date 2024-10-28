package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.ApiUser;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.UserResponse;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        User user = UserMapper.userRequestToUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.userToUserResponse(userInPort.create(user)));
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(UserMapper.usersToUserResponses(userInPort.getAll()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(UserMapper.userToUserResponse(userInPort.findById(id)));
    }

    @SneakyThrows
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userInPort.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable long id, @RequestBody UserRequest userRequest) {
        User user = UserMapper.userRequestToUser(userRequest);
        return ResponseEntity.ok(UserMapper.userToUserResponse(userInPort.update(id, user)));
    }


}
