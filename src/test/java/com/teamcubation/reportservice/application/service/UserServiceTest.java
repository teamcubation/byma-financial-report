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
    private Long id = 8L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(id)
                .username("test1")
                .password("1234")
                .email("test@gmail.com")
                .role(UserRole.USER)
                .build();
    }

    @Test
    void shouldReturnAUser_whenCreateAUser() {

        when(userOutPort.registerUser(user)).thenReturn(user);

        User result = userService.create(user);
        assertEquals(result.getEmail(), user.getEmail());
        assertEquals(result.getRole(), user.getRole());
        assertEquals(result.getPassword(), user.getPassword());
    }

    @Test
    void shouldReturnExceptionDuplicateUser_whenCreateAUserWithDuplicateName() {
        User userWithDuplicatedName = User.builder()
                .username("test")
                .password("1234")
                .email("test2@gmail.com")
                .role(UserRole.USER)
                .build();

        when(userOutPort.existsByNameIgnoreCase(userWithDuplicatedName.getUsername())).thenReturn(true);

        assertThrows(UserDuplicateException.class, () -> userService.create(userWithDuplicatedName));
    }

    @Test
    void shouldReturnExceptionDuplicateUser_whenCreateAUserWithDuplicateEmail() {
        User userWithDuplicatedEmail = User.builder()
                .username("test2")
                .password("1234")
                .email("test@gmail.com")
                .role(UserRole.USER)
                .build();

        when(userOutPort.existsByEmailIgnoreCase(userWithDuplicatedEmail.getEmail())).thenReturn(true);

        assertThrows(UserDuplicateException.class, () -> userService.create(userWithDuplicatedEmail));
    }

    @Test
    void shouldReturnAUser_WhenFindAUserById() {
        user.setId(id);

        when(userOutPort.findById(id)).thenReturn(user);

        User result = userService.findById(id);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void shouldThrowUserNotFoundException_whenNotFoundAId() {
        when(userOutPort.findById(id)).thenThrow(new UserNotFoundException());
        assertThrows(UserNotFoundException.class, () -> userService.findById(id));
    }

//    @Test
//    void shouldReturnUserUpdateEmail_whenUpdateEmail() throws Exception {
//        User userUpdateEmail = user.builder()
//                        .email("newEmail@gmail.com")
//                        .password(null)
//                        .role(null)
//                        .username(null)
//                        .build();
//
//        when(userOutPort.findById(id)).thenReturn(user);
//        user.setEmail("newEmail@gmail.com");
//        when(userOutPort.updateUser(userUpdateEmail)).thenReturn(user);
//        when(userOutPort.existsByEmailIgnoreCase(user.getEmail())).thenReturn(false);
//
//        User result = userService.update(id, userUpdateEmail);
//        assertEquals(user.getEmail(), result.getEmail());
//        assertEquals(user.getRole(), result.getRole());
//        assertEquals(user.getPassword(), result.getPassword());
//    }
//
//    @Test
//    void shouldReturnUserWithUpdateUserName_whenUpdateUserName() throws Exception {
//        User userToUpdate = user.builder()
//                .username("Test100000")
//                .email(null)
//                .password(null)
//                .role(null)
//                .build();
//
//        when(userOutPort.findById(id)).thenReturn(user);
//        when(userOutPort.existsByNameIgnoreCase(user.getUsername())).thenReturn(false);
//        user.setUsername("Test100000");
//        when(userOutPort.updateUser(userToUpdate)).thenReturn(user);
//
//        //User userUpdate = userService.update(id,userToUpdate);
//        assertEquals(user,userService.update(id,userToUpdate));
//    }

    @Test
    void shouldThrowUserNotFoundException_whenDeleteUserWithInexistentId() {
        when(userOutPort.findById(id)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.delete(id));
    }

    @Test
    void shouldDeleteAUser_whenDeleteUser() throws Exception {
        when(userOutPort.findById(id)).thenReturn(user);
        doNothing().when(userOutPort).deleteUserById(id);
        userService.delete(id);

        verify(userOutPort,times(1)).deleteUserById(id);
    }

    @Test
    void shouldReturnAllUsers_whenGetAllUsers() {
        List<User> usersTest = new ArrayList<>();
        usersTest.add(user);
        usersTest.add(user);
        usersTest.add(user);

        when(userOutPort.getAll()).thenReturn(usersTest);
        List<User> result = userService.getAll();
        assertEquals(usersTest.size(), result.size());
        for (User userResult : result){
            assertEquals(user, userResult);
        }
    }
}