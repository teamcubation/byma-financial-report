package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.exceptionHandler.GlobalExceptionHandler;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
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
    private static final Long ID_1 = 1L;
    private static final long ID_2 = 2L;
    private static final long ID_3 = 3L;
    private static final String EMAIL_1 = "email1@gmail.com";
    private static final String EMAIL_2 = "email2@gmail.com";
    private static final String EMAIL_3 = "email3@gmail.com";
    private static final String NAME_1 = "name_1";
    private static final String NAME_2 = "name_2";
    private static final String NAME_3 = "name_3";
    private static final String PASSWORD_1 = "password_1";
    private static final String PASSWORD_2 = "password_2";
    private static final String PASSWORD_3 = "password_3";
    private static final String ROLE_1 = "USER";
    private static final long ID_NONEXISTENT = 8L;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserInPort userInPort;

    private MockMvc mockMvc;

    private List<User> users = new ArrayList<>();

    private  UserRequest userRequest;

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

        userRequest = UserRequest.builder()
                .email(EMAIL_1)
                .username(NAME_1)
                .role(ROLE_1)
                .password(PASSWORD_1)
                .build();
    }

    @Test
    void shouldRegisterAUserSuccess_whenCreateAUser() throws Exception {
        User userCreate = UserMapper.userRequestToUser(ID_1, userRequest);

        when(userInPort.create(UserMapper.userRequestToUser(null, userRequest))).thenReturn(userCreate);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(NAME_1))
                .andExpect(jsonPath("$.email").value(EMAIL_1))
                .andExpect(jsonPath("$.password").value(PASSWORD_1));
    }

    @Test
    void shouldReturnStatusCode409_whenUserToCreateAlreadyExists() throws Exception {
        when(userInPort.create(UserMapper.userRequestToUser(null, userRequest))).thenThrow(new UserDuplicateException("User Duplicated"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User Duplicated"));
    }

    @Test
    void shouldReturnAllUsersSuccessAndHasSize3_whenGetAll() throws Exception {
        when(userInPort.getAll()).thenReturn(users);

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    void shouldReturnAndEmptyList_whenGetAllAndThereIsNotUsers() throws Exception {
        when(userInPort.getAll()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnBadRequest_whenCreateUserWithNameNull() throws Exception {
        userRequest.setUsername(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenwhenCreateUserEmailNull() throws Exception {
        userRequest.setEmail(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateUserWithPasswordNull() throws Exception {
        userRequest.setPassword(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateUserWithRoleNull() throws Exception {
        userRequest.setRole(null);

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
                .andExpect(jsonPath("$.username").value(NAME_1))
                .andExpect(jsonPath("$.email").value(EMAIL_1))
                .andExpect(jsonPath("$.password").value(PASSWORD_1));
    }

    @Test
    void shouldReturnStatusCode409_whenGetUserByIdIsNotFound() throws Exception {
        when(userInPort.findById(ID_1)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/{id}", ID_1))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturnStatusCode204_whenDeleteUserByIdIsSuccess() throws Exception {
        doNothing().when(userInPort).delete(ID_1);

        mockMvc.perform(delete("/api/users/{id}", ID_1))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnStatusCode409_whenDeleteUserByIdIsNotFound() throws Exception {
        doThrow(new UserNotFoundException("User not found")).when(userInPort).delete(ID_1);

        mockMvc.perform(delete("/api/users/{id}", ID_1))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturnUpdatedUser_whenUpdateUserByIdIsSuccess() throws Exception {
        UserRequest userRequestToUpdate = UserRequest.builder()
                .email(EMAIL_2)
                .username(NAME_2)
                .role(ROLE_1)
                .password(PASSWORD_2)
                .build();

        User userToUpdate = UserMapper.userRequestToUser(ID_1,userRequest);
        when(userInPort.update(ID_1, UserMapper.userRequestToUser(null, userRequestToUpdate))).thenReturn(userToUpdate);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequestToUpdate);

        mockMvc.perform(put("/api/users/{id}", ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username").value(NAME_2))
                .andExpect(jsonPath("$.email").value(EMAIL_2))
                .andExpect(jsonPath("$.password").value(PASSWORD_2));
    }

    @Test
    void shouldReturnStatusCode409_whenUpdateUserWithNameDuplicated() throws Exception {
        UserRequest userRequestToUpdateWithSameName = UserRequest.builder()
                .email(EMAIL_2)
                .username("duplicated name")
                .role(ROLE_1)
                .password(PASSWORD_2)
                .build();

        when(userInPort.update(ID_1, UserMapper.userRequestToUser(null, userRequestToUpdateWithSameName))).thenThrow(new UserDuplicateException("User Duplicated"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequestToUpdateWithSameName);

        mockMvc.perform(put("/api/users/{id}", ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User Duplicated"));
    }

    @Test
    void shouldReturnStatusCode409_whenUpdateUserWithEmailDuplicated() throws Exception {
        UserRequest userRequestToUpdateWithSameEmail = UserRequest.builder()
                .email("duplicated email")
                .username(NAME_2)
                .role(ROLE_1)
                .password(PASSWORD_2)
                .build();

        when(userInPort.update(ID_1, UserMapper.userRequestToUser(null, userRequestToUpdateWithSameEmail))).thenThrow(new UserDuplicateException("User Duplicated"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequestToUpdateWithSameEmail);

        mockMvc.perform(put("/api/users/{id}", ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User Duplicated"));
    }

    @Test
    void shouldReturnStatusCode409_whenUpdateUserIsNotFound() throws Exception {
        when(userInPort.update(ID_NONEXISTENT, UserMapper.userRequestToUser(null, userRequest))).thenThrow(new UserNotFoundException("User not found"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(put("/api/users/{id}", ID_NONEXISTENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}