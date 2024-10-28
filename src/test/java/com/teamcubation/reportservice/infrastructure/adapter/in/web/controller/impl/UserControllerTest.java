package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.exceptionHandler.GlobalExceptionHandler;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.ArrayList;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserControllerTest {
    private final static long ID_1 = 1L;
    private final static long ID_2 = 2L;
    private final static long ID_3 = 3L;
    private final static String EMAIL_1 = "email1@gmail.com";
    private final static String EMAIL_2 = "email2@gmail.com";
    private final static String EMAIL_3 = "email3@gmail.com";
    private final static String NAME_1 = "name_1";
    private final static String NAME_2 = "name_2";
    private final static String NAME_3 = "name_3";
    private final static String PASSWORD_1 = "password_1";
    private final static String PASSWORD_2 = "password_2";
    private final static String PASSWORD_3 = "password_3";

    @InjectMocks
    private UserController userController;

    @Mock
    private UserInPort userInPort;

    private MockMvc mockMvc;

    List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        users.add(User.builder()
                .id(ID_1)
                .email(EMAIL_1)
                .username(NAME_1)
                .role(UserRole.USER)
                .password(PASSWORD_1)
                .build());
        users.add(User.builder()
                .id(ID_2)
                .email(EMAIL_2)
                .username(NAME_2)
                .role(UserRole.USER)
                .password(PASSWORD_2)
                .build());
        users.add(User.builder()
                .id(ID_3)
                .email(EMAIL_3)
                .username(NAME_3)
                .role(UserRole.USER)
                .password(PASSWORD_3)
                .build());
    }

    @Test
    void shouldRegisterAUserSuccess_whenRegisterAUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(EMAIL_1)
                .username(NAME_1)
                .role(UserRole.USER.toString())
                .password(PASSWORD_1)
                .build();
        when(userInPort.create(UserMapper.userRequestToUser(userRequest))).thenReturn(UserMapper.userRequestToUser(userRequest));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(NAME_1));
    }

    @Test
    void shouldReturnStatusCode409_whenUserAlreadyExists() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(EMAIL_1)
                .username(NAME_1)
                .role(UserRole.USER.toString())
                .password(PASSWORD_1)
                .build();
        when(userInPort.create(UserMapper.userRequestToUser(userRequest))).thenThrow(new UserDuplicateException("User Duplicated"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User Duplicated"));
    }

    @Test
    void shouldReturnAllUsersSuccessAndHasSize3_whenGetAllUsers() throws Exception {
        when(userInPort.getAll()).thenReturn(users);

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    void shouldReturnAndEmptyList_whenThereIsNoUsers() throws Exception {
        when(userInPort.getAll()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnBadRequest_whenNameIsNull() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(EMAIL_1)
                .username(null)
                .role(UserRole.USER.toString())
                .password(PASSWORD_1)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenEmailIsNull() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(null)
                .username(NAME_1)
                .role(UserRole.USER.toString())
                .password(PASSWORD_1)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenPasswordIsNull() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(EMAIL_1)
                .username(NAME_1)
                .role(UserRole.USER.toString())
                .password(null)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenRoleIsNull() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(EMAIL_1)
                .username(NAME_1)
                .role(null)
                .password(PASSWORD_1)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnAUserSuccess_whenGetUserById() throws Exception {
        when(userInPort.findById(ID_1)).thenReturn(users.get(0));

        mockMvc.perform(get("/api/users/{id}", ID_1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username").value(NAME_1));
    }

    @Test
    void shouldReturnStatusCode409_whenGetUserByIdIsNotFound() throws Exception {
        when(userInPort.findById(ID_1)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/{id}", ID_1))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturnStatusCode409_whenDeleteUserByIdIsNotFound() throws Exception {
        doThrow(new UserNotFoundException("User not found")).when(userInPort).delete(ID_1);

        mockMvc.perform(delete("/api/users/{id}", ID_1))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturnStatusCode204_whenDeleteUserByIdIsSuccess() throws Exception {
        doNothing().when(userInPort).delete(ID_1);

        mockMvc.perform(delete("/api/users/{id}", ID_1))
                .andExpect(status().isNoContent());
    }

}