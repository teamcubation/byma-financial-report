package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.application.port.in.UserInPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.exceptionHandler.GlobalExceptionHandler;
import com.teamcubation.reportservice.exceptionHandler.utils.MessageConstants;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import com.teamcubation.reportservice.domain.model.user.Rol.Role;

import java.util.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.teamcubation.reportservice.domain.model.user.UserRole.*;
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
    private static final String BASE_URL = "/api/users/";

    @InjectMocks
    private UserController userController;

    @Mock
    private UserInPort userInPort;

    private MockMvc mockMvc;

    private List<User> users = new ArrayList<>();

    private  UserRequest userRequest;

    private Set<String> role_Request = new HashSet<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        Set<Role> role_1 = new HashSet<>();
        role_1.add(Role.builder()
                .id(ID_1)
                .role(USER)
                .build());

        Set<Role> role_2 = new HashSet<>();
        role_1.add(Role.builder()
                .id(ID_2)
                .role(USER)
                .build());

        Set<Role> role_3 = new HashSet<>();
        role_1.add(Role.builder()
                .id(ID_3)
                .role(USER)
                .build());

        users.add(User.builder()
                .id(ID_1)
                .email(EMAIL_1)
                .username(NAME_1)
                .roles(role_1)
                .password(PASSWORD_1)
                .build());
        users.add(User.builder()
                .id(ID_2)
                .email(EMAIL_2)
                .username(NAME_2)
                .roles(role_2)
                .password(PASSWORD_2)
                .build());
        users.add(User.builder()
                .id(ID_3)
                .email(EMAIL_3)
                .username(NAME_3)
                .roles(role_3)
                .password(PASSWORD_3)
                .build());

        role_Request.add(UserRole.USER.toString());

        userRequest = UserRequest.builder()
                .email(EMAIL_1)
                .username(NAME_1)
                .roles(role_Request)
                .password(PASSWORD_1)
                .build();
    }

    @Test
    void shouldRegisterAUserSuccess_whenCreateAUser() throws Exception {
        User userCreate = UserMapper.userRequestToUser(ID_1, userRequest);

        when(userInPort.create(UserMapper.userRequestToUser(null, userRequest))).thenReturn(userCreate);

        String jsonContent = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(NAME_1))
                .andExpect(jsonPath("$.email").value(EMAIL_1))
                .andExpect(jsonPath("$.password").value(PASSWORD_1));
    }

    @Test
    void shouldReturnStatusCode409_whenUserToCreateHasADuplicatedName() throws Exception {
        when(userInPort.create(UserMapper.userRequestToUser(null, userRequest))).thenThrow(new UserDuplicateException(MessageConstants.DUPLICATED_USERNAME_USER));

        String jsonContent = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(MessageConstants.DUPLICATED_USERNAME_USER));
    }

    @Test
    void shouldReturnStatusCode409_whenUserToCreateHasADuplicatedEmail() throws Exception {
        when(userInPort.create(UserMapper.userRequestToUser(null, userRequest))).thenThrow(new UserDuplicateException(MessageConstants.DUPLICATED_EMAIL_USER));

        String jsonContent = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(MessageConstants.DUPLICATED_EMAIL_USER));
    }

    @Test
    void shouldReturnAllUsersSuccessAndHasSize3_whenGetAll() throws Exception {
        when(userInPort.getAll()).thenReturn(users);

        this.mockMvc.perform(get(BASE_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    void shouldReturnAndEmptyList_whenGetAllAndThereIsNotUsers() throws Exception {
        when(userInPort.getAll()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get(BASE_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnBadRequest_whenCreateUserWithNameNull() throws Exception {
        userRequest.setUsername(null);

        String jsonContent = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenwhenCreateUserEmailNull() throws Exception {
        userRequest.setEmail(null);

        String jsonContent = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateUserWithPasswordNull() throws Exception {
        userRequest.setPassword(null);

        String jsonContent = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateUserWithRoleNull() throws Exception {
        userRequest.setRoles(Collections.emptySet());

        String jsonContent = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnAUserSuccess_whenGetUserById() throws Exception {
        when(userInPort.findById(ID_1)).thenReturn(users.get(0));

        mockMvc.perform(get(BASE_URL + "{id}", ID_1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username").value(NAME_1))
                .andExpect(jsonPath("$.email").value(EMAIL_1))
                .andExpect(jsonPath("$.password").value(PASSWORD_1));
    }

    @Test
    void shouldReturnStatusCode404_whenGetUserByIdIsNotFound() throws Exception {
        when(userInPort.findById(ID_1)).thenThrow(new UserNotFoundException(MessageConstants.USER_NOT_FOUND));

        mockMvc.perform(get(BASE_URL + "{id}", ID_1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MessageConstants.USER_NOT_FOUND));
    }

    @Test
    void shouldReturnStatusCode204_whenDeleteUserByIdIsSuccess() throws Exception {
        doNothing().when(userInPort).delete(ID_1);

        mockMvc.perform(delete(BASE_URL + "{id}", ID_1))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnStatusCode404_whenDeleteUserByIdIsNotFound() throws Exception {
        doThrow(new UserNotFoundException(MessageConstants.USER_NOT_FOUND)).when(userInPort).delete(ID_1);

        mockMvc.perform(delete(BASE_URL + "{id}", ID_1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MessageConstants.USER_NOT_FOUND));
    }

    @Test
    void shouldReturnUpdatedUser_whenUpdateUserByIdIsSuccess() throws Exception {
        UserRequest userRequestToUpdate = UserRequest.builder()
                .email(EMAIL_2)
                .username(NAME_2)
                .roles(role_Request)
                .password(PASSWORD_2)
                .build();

        User userToUpdate = UserMapper.userRequestToUser(ID_1, userRequestToUpdate);
        when(userInPort.update(UserMapper.userRequestToUser(ID_1, userRequestToUpdate))).thenReturn(userToUpdate);

        String jsonContent = new ObjectMapper().writeValueAsString(userRequestToUpdate);

        mockMvc.perform(put(BASE_URL + "{id}", ID_1)
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
                .roles(role_Request)
                .password(PASSWORD_2)
                .build();

        when(userInPort.update(UserMapper.userRequestToUser(ID_1, userRequestToUpdateWithSameName)))
                .thenThrow(new UserDuplicateException(MessageConstants.DUPLICATED_USERNAME_USER));

        String jsonContent = new ObjectMapper().writeValueAsString(userRequestToUpdateWithSameName);

        mockMvc.perform(put(BASE_URL + "{id}", ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(MessageConstants.DUPLICATED_USERNAME_USER));
    }

    @Test
    void shouldReturnStatusCode409_whenUpdateUserWithEmailDuplicated() throws Exception {
        UserRequest userRequestToUpdateWithSameEmail = UserRequest.builder()
                .email("duplicated email")
                .username(NAME_2)
                .roles(role_Request)
                .password(PASSWORD_2)
                .build();

        when(userInPort.update(UserMapper.userRequestToUser(ID_1, userRequestToUpdateWithSameEmail))).thenThrow(new UserDuplicateException(MessageConstants.DUPLICATED_EMAIL_USER));

        String jsonContent = new ObjectMapper().writeValueAsString(userRequestToUpdateWithSameEmail);

        mockMvc.perform(put(BASE_URL + "{id}", ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(MessageConstants.DUPLICATED_EMAIL_USER));
    }

    @Test
    void shouldReturnStatusCode404_whenUpdateUserIsNotFound() throws Exception {
        when(userInPort.update(UserMapper.userRequestToUser(ID_NONEXISTENT, userRequest))).thenThrow(new UserNotFoundException(MessageConstants.USER_NOT_FOUND));

        String jsonContent = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(put(BASE_URL + "{id}", ID_NONEXISTENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MessageConstants.USER_NOT_FOUND));
    }
}