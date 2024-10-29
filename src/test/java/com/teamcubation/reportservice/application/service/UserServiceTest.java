package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserOutPort userOutPort;

    private User user;
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
    private static final long ID_NONEXISTENT = 8L;


    private List<User> users = new ArrayList<>();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

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

        user =  User.builder()
                .id(ID_1)
                .email(EMAIL_1)
                .username(NAME_1)
                .role(UserRole.USER)
                .password(PASSWORD_1).build();
    }

    @Test
    void shouldReturnAUser_whenCreateAUser() {
        when(userOutPort.registerUser(user)).thenReturn(user);

        User result = userService.create(user);
        assertEquals(result.getEmail(), user.getEmail());
        assertEquals(result.getRole(), user.getRole());
        assertEquals(result.getPassword(), user.getPassword());
        assertEquals(result.getUsername(), user.getUsername());
    }

    @Test
    void shouldReturnExceptionDuplicateUser_whenCreateAUserWithDuplicateName() {
        when(userOutPort.existsByNameIgnoreCase(user.getUsername())).thenReturn(true);

        assertThrows(UserDuplicateException.class, () -> userService.create(user));
    }

    @Test
    void shouldReturnExceptionDuplicateUser_whenCreateAUserWithDuplicateEmail() {
        when(userOutPort.existsByEmailIgnoreCase(user.getEmail())).thenReturn(true);

        assertThrows(UserDuplicateException.class, () -> userService.create(user));
    }

    @Test
    void shouldReturnAUser_WhenFindAUserById() {
        when(userOutPort.findById(ID_1)).thenReturn(user);

        User result = userService.findById(ID_1);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void shouldThrowUserNotFoundException_whenNotFoundAId() {
        when(userOutPort.findById(ID_NONEXISTENT)).thenThrow(new UserNotFoundException());
        assertThrows(UserNotFoundException.class, () -> userService.findById(ID_NONEXISTENT));
    }

    @Test
    void shouldReturnUserUpdateEmail_whenUpdateEmail() throws Exception {
        User userToUpdate = User.builder()
                        .email("newEmail@gmail.com")
                        .build();

        when(userOutPort.findById(ID_1)).thenReturn(user);
        when(userOutPort.existsByEmailIgnoreCase(userToUpdate.getEmail())).thenReturn(false);
        user.setEmail("newEmail@gmail.com");
        when(userOutPort.updateUser(user)).thenReturn(user);

        User result = userService.update(ID_1, userToUpdate);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void shouldReturnUserWithUpdateUserName_whenUpdateUserName() throws Exception {
        User userToUpdate = User.builder()
                .username("newName")
                .build();

        when(userOutPort.findById(ID_1)).thenReturn(user);
        when(userOutPort.existsByNameIgnoreCase(userToUpdate.getUsername())).thenReturn(false);
        user.setUsername("newName");
        when(userOutPort.updateUser(user)).thenReturn(user);

        User result = userService.update(ID_1, userToUpdate);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void shouldReturnUserWithUpdatePassword_whenUpdatePassword() throws Exception {
        User userToUpdate = User.builder()
                .password("newPassword")
                .build();

        when(userOutPort.findById(ID_1)).thenReturn(user);
        user.setPassword("newPassword");
        when(userOutPort.updateUser(user)).thenReturn(user);

        User result = userService.update(ID_1, userToUpdate);

        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void shouldReturnUserWithUpdateRole_whenUpdateRole() throws Exception {
        User userToUpdate = User.builder()
                .role(UserRole.ADMIN)
                .build();

        when(userOutPort.findById(ID_1)).thenReturn(user);
        user.setRole(UserRole.ADMIN);
        when(userOutPort.updateUser(user)).thenReturn(user);

        User result = userService.update(ID_1, userToUpdate);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void shouldReturnExceptionDuplicateUser_whenUpdateAUserWithDuplicateEmail() {
        User userToUpdate = User.builder()
                .email("duplicated email")
                .build();

        when(userOutPort.findById(ID_1)).thenReturn(user);
        when(userOutPort.existsByEmailIgnoreCase(userToUpdate.getEmail())).thenReturn(true);

        assertThrows(UserDuplicateException.class, () -> userService.update(ID_1, userToUpdate));
    }

    @Test
    void shouldReturnExceptionDuplicateUser_whenUpdateAUserWithDuplicateName() {
        User userToUpdate = User.builder()
                .username("duplicated name")
                .build();

        when(userOutPort.findById(ID_1)).thenReturn(user);
        when(userOutPort.existsByNameIgnoreCase(userToUpdate.getUsername())).thenReturn(true);

        assertThrows(UserDuplicateException.class, () -> userService.update(ID_1, userToUpdate));
    }

    @Test
    void shouldDeleteAUser_whenDeleteUser() throws Exception {
        when(userOutPort.findById(ID_1)).thenReturn(user);
        doNothing().when(userOutPort).deleteUserById(ID_1);
        userService.delete(ID_1);

        verify(userOutPort,times(1)).deleteUserById(ID_1);
    }

    @Test
    void shouldThrowUserNotFoundException_whenDeleteUserWithInexistentId() {
        when(userOutPort.findById(ID_NONEXISTENT)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.delete(ID_1));
    }

    @Test
    void shouldReturnAllUsers_whenGetAllUsers() {
        when(userOutPort.getAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertEquals(users.size(), result.size());
        for (int i = 0; i < result.size(); i++){
            assertEquals(users.get(i), result.get(i));
        }
    }
}