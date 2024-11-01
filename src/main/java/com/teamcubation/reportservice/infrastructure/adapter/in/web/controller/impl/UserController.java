package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.RoleOutPort;
import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.service.exception.InvalidUserModel;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.exceptionHandler.utils.MessageConstants;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.ApiUser;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserUpdateRequestDTO;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response.UserResponse;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.validation.ControllerValidator;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.util.AnsiColor;
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
    private final RoleOutPort roleOutPort;

    private static final String URL = "/api/users";
    private static final String METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";

    @Override
    @PostMapping()
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException, InvalidUserModel {

        log.info(AnsiColor.BLUE + "Started user request received in curl format: {}\n" + AnsiColor.RESET, CurlGenerator.generateCurl(URL, METHOD, CONTENT_TYPE, userRequest));
        UserResponse userResponse = UserMapper.userToUserResponse(userInPort.create(UserMapper.userRequestToUser(null, userRequest)));
        log.info(AnsiColor.BLUE + "Finished user response created: {}\n" + AnsiColor.RESET, userResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll() throws InvalidUserModel, UserNotFoundException {
        log.info(AnsiColor.BLUE + "Started getting all users" + AnsiColor.RESET);
        List<UserResponse> userResponses = UserMapper.usersToUserResponses(userInPort.getAll());
        log.info(AnsiColor.BLUE + "Finished users found finished" + AnsiColor.RESET);
        return ResponseEntity.ok(userResponses);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) throws UserNotFoundException, UserEntityNotFoundException, InvalidUserModel {
        log.info(AnsiColor.BLUE + "Started getting user by id: {}" + AnsiColor.RESET, id);
        UserResponse userResponse = UserMapper.userToUserResponse(userInPort.findById(id));
        log.info(AnsiColor.BLUE + "Finished user found: {}" + AnsiColor.RESET, userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws UserNotFoundException, UserEntityNotFoundException {
        log.info(AnsiColor.BLUE + "Stared deleting user by id: {}" + AnsiColor.RESET, id);
        userInPort.delete(id);
        log.info(AnsiColor.BLUE + "Finished user deleted" + AnsiColor.RESET);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable long id, @RequestBody UserRequest userRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException, InvalidUserModel {
        validateParams(userRequest);
        log.info(AnsiColor.BLUE + "Started updating user by id: {} with data: {}" + AnsiColor.RESET, id, userRequest);
        UserResponse userResponse = UserMapper.userToUserResponse(userInPort.update(UserMapper.userRequestToUser(id, userRequest)));
        log.info(AnsiColor.BLUE + "Finished user updated: {}" + AnsiColor.RESET, userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("public/{id}")
    public ResponseEntity<UserResponse> updateRegularUser(@PathVariable long id, @RequestBody UserUpdateRequestDTO userRequest) throws UserNotFoundException, UserEntityNotFoundException, UserDuplicateException, InvalidUserModel {
        validateParams(userRequest);
        log.info(AnsiColor.BLUE + "Started updating user by id: {} with data: {}" + AnsiColor.RESET, id, userRequest);
        UserResponse userResponse = UserMapper.userToUserResponse(userInPort.update(UserMapper.userUpdateRequestDTOToUser(id, userRequest)));
        log.info(AnsiColor.BLUE + "Finished user updated: {}" + AnsiColor.RESET, userResponse);
        return ResponseEntity.ok(userResponse);
    }

    private void validateParams(Object... params) throws InvalidUserModel {
        if (ControllerValidator.isNull(params)) {
            log.error(AnsiColor.BLUE + "Params cannot be null" + AnsiColor.RESET);
            throw new InvalidUserModel(MessageConstants.INVALID_USER_MODEL);
        }
    }
}