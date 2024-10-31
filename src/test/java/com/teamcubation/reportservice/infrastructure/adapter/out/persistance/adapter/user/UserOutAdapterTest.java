package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserOutAdapterTest {

    private static final Long USER_ID = 1L;
    private static final String USERNAME = "testUser";
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password";
    private static final String USER_NOT_FOUND_MESSAGE = "User Entity not found";
    private static final String PARAMS_CANNOT_BE_NULL_MESSAGE = "Parameters cannot be null";
    private static final String NON_EXISTENT_EMAIL = "nonexistent@example.com";
    private static final String NON_EXISTENT_USERNAME = "nonexistentUser";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserOutAdapter userOutAdapter;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(USER_ID)
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .roles(new HashSet<>())
                .build();

        userEntity = UserEntity.builder()
                .id(USER_ID)
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .roles(new HashSet<>())
                .build();
    }

    @Test
    public void registerUser_shouldReturnUser_whenUserIsRegistered() throws UserEntityNotFoundException, UserNotFoundException {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User result = userOutAdapter.registerUser(user);

        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void registerUser_shouldThrowException_whenUserIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userOutAdapter.registerUser(null);
        });

        assertEquals(PARAMS_CANNOT_BE_NULL_MESSAGE, exception.getMessage());
    }

    @Test
    public void findByEmailIgnoreCase_shouldReturnUser_whenUserExists() throws UserEntityNotFoundException, UserNotFoundException {
        when(userRepository.findByEmailIgnoreCase(EMAIL)).thenReturn(Optional.of(userEntity));

        User result = userOutAdapter.findByEmailIgnoreCase(EMAIL);

        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).findByEmailIgnoreCase(EMAIL);
    }

    @Test
    public void findByEmailIgnoreCase_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findByEmailIgnoreCase(EMAIL)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserEntityNotFoundException.class, () -> {
            userOutAdapter.findByEmailIgnoreCase(EMAIL);
        });

        assertEquals(USER_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    public void findByUsername_shouldReturnUser_whenUserExists() throws UserNotFoundException, UserEntityNotFoundException {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));

        User result = userOutAdapter.findByUsername(USERNAME);

        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    public void findByUsername_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserEntityNotFoundException.class, () -> {
            userOutAdapter.findByUsername(USERNAME);
        });

        assertEquals(USER_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    public void findById_shouldReturnUser_whenUserExists() throws UserNotFoundException, UserEntityNotFoundException {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(userEntity));

        User result = userOutAdapter.findById(USER_ID);

        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).findById(USER_ID);
    }

    @Test
    public void findById_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserEntityNotFoundException.class, () -> {
            userOutAdapter.findById(USER_ID);
        });

        assertEquals(USER_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    public void getAll_shouldReturnListOfUsers_whenUsersExist() throws UserNotFoundException {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity);
        when(userRepository.findAll()).thenReturn(userEntities);

        List<User> result = userOutAdapter.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(USERNAME, result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getAll_shouldReturnEmptyList_whenNoUsersExist() throws UserNotFoundException {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<User> result = userOutAdapter.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void updateUser_shouldReturnUpdatedUser_whenUserIsUpdated() throws UserNotFoundException, UserEntityNotFoundException {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User result = userOutAdapter.updateUser(user);

        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void updateUser_shouldThrowException_whenUserIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userOutAdapter.updateUser(null);
        });

        assertEquals(PARAMS_CANNOT_BE_NULL_MESSAGE, exception.getMessage());
    }

    @Test
    public void deleteUserById_shouldDeleteUser_whenUserExists() throws UserEntityNotFoundException {
        when(userRepository.existsById(USER_ID)).thenReturn(true);

        userOutAdapter.deleteUserById(USER_ID);

        verify(userRepository, times(1)).deleteById(USER_ID);
    }

    @Test
    public void deleteUserById_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.existsById(USER_ID)).thenReturn(false);

        Exception exception = assertThrows(UserEntityNotFoundException.class, () -> {
            userOutAdapter.deleteUserById(USER_ID);
        });

        assertEquals(USER_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    public void existsByEmailIgnoreCase_shouldReturnTrue_whenEmailExists() {
        when(userRepository.existsByEmailIgnoreCase(EMAIL)).thenReturn(true);

        boolean exists = userOutAdapter.existsByEmailIgnoreCase(EMAIL);

        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmailIgnoreCase(EMAIL);
    }

    @Test
    public void existsByEmailIgnoreCase_shouldReturnFalse_whenEmailDoesNotExist() {
        when(userRepository.existsByEmailIgnoreCase(NON_EXISTENT_EMAIL)).thenReturn(false);

        boolean exists = userOutAdapter.existsByEmailIgnoreCase(NON_EXISTENT_EMAIL);

        assertFalse(exists);
        verify(userRepository, times(1)).existsByEmailIgnoreCase(NON_EXISTENT_EMAIL);
    }

    @Test
    public void existsByNameIgnoreCase_shouldReturnTrue_whenUsernameExists() {
        when(userRepository.existsByUsernameIgnoreCase(USERNAME)).thenReturn(true);

        boolean exists = userOutAdapter.existsByNameIgnoreCase(USERNAME);

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsernameIgnoreCase(USERNAME);
    }

    @Test
    public void existsByNameIgnoreCase_shouldReturnFalse_whenUsernameDoesNotExist() {
        when(userRepository.existsByUsernameIgnoreCase(NON_EXISTENT_USERNAME)).thenReturn(false);

        boolean exists = userOutAdapter.existsByNameIgnoreCase(NON_EXISTENT_USERNAME);

        assertFalse(exists);
        verify(userRepository, times(1)).existsByUsernameIgnoreCase(NON_EXISTENT_USERNAME);
    }
}