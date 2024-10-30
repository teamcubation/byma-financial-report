package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user;

import com.teamcubation.reportservice.application.service.exception.InvalidUserModel;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.UserPersistenceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserOutAdapterTest {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final Long ID_1 = 1L;
    private static final long ID_2 = 2L;
    private static final long ID_3 = 3L;
    private static final String EMAIL_1 = "email1@gmail.com";
    private static final String EMAIL_2 = "email2@gmail.com";
    private static final String EMAIL_3 = "email3@gmail.com";
    private static final String PASSWORD_1 = "password_1";
    private static final String PASSWORD_2 = "password_2";
    private static final String PASSWORD_3 = "password_3";
    private static final String USERNAME_1 = "username_1";
    private static final String USERNAME_2 = "username_2";
    private static final String USERNAME_3 = "username_3";
    private static final long INVALID_ID = -1L;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserOutAdapter userOutAdapter;

    private UserRequest mockedUserRequest;

    private String mockedEmailRequest;

    private String mockedUsernameRequest;

    private Long mockedUserIdRequest;

    private List<UserEntity> mockedUserEntitiesFromDb;

    private UserEntity expectedUserEntity;

    @BeforeEach
    public void setUp() {
        mockedUserRequest = UserRequest.builder()
                .username(USERNAME_1)
                .email(EMAIL_1)
                .password(PASSWORD_1)
                .role(UserRole.ADMIN.toString())
                .build();

        mockedEmailRequest = EMAIL_2;

        mockedUsernameRequest = USERNAME_2;

        mockedUserIdRequest = ID_1;

        mockedUserEntitiesFromDb = List.of(
                UserEntity.builder()
                        .id(ID_1)
                        .username(USERNAME_1)
                        .email(EMAIL_1)
                        .password(PASSWORD_1)
                        .role(UserRole.ADMIN)
                        .build(),
                UserEntity.builder()
                        .id(ID_2)
                        .username(USERNAME_2)
                        .email(EMAIL_2)
                        .password(PASSWORD_2)
                        .role(UserRole.ADMIN)
                        .build(),
                UserEntity.builder()
                        .id(ID_3)
                        .username(USERNAME_3)
                        .email(EMAIL_3)
                        .password(PASSWORD_3)
                        .role(UserRole.ADMIN)
                        .build()
        );

        expectedUserEntity = UserEntity.builder()
                .id(ID_1)
                .username(USERNAME_1)
                .email(EMAIL_1)
                .password(PASSWORD_1)
                .role(UserRole.ADMIN)
                .build();

    }

    @Test
    public void shouldRegisterUser_whenValidUserIsProvided_thenReturnPersistedUser() throws UserEntityNotFoundException, UserNotFoundException, InvalidUserModel {

        User userFromRequest = UserMapper.userRequestToUser(null, mockedUserRequest);

        when(userRepository.save(UserPersistenceMapper.userToUserEntity(userFromRequest))).thenReturn(expectedUserEntity);

        User user = userOutAdapter.registerUser(userFromRequest);

        assertNotNull(user);
        assertEquals(expectedUserEntity.getId(), user.getId());
        assertEquals(expectedUserEntity.getUsername(), user.getUsername());
        assertEquals(expectedUserEntity.getEmail(), user.getEmail());
        assertEquals(expectedUserEntity.getPassword(), user.getPassword());
        assertEquals(expectedUserEntity.getRole(), user.getRole());
    }

    @Test
    void shouldFindByEmail_whenValidEmailIsProvided_thenReturnPersistedUser() throws Exception {
        when(userRepository.findByEmailIgnoreCase(mockedEmailRequest)).thenReturn(Optional.of(expectedUserEntity));

        User user = userOutAdapter.findByEmailIgnoreCase(mockedEmailRequest);

        assertNotNull(user);
        assertEquals(expectedUserEntity.getId(), user.getId());
        assertEquals(expectedUserEntity.getUsername(), user.getUsername());
        assertEquals(expectedUserEntity.getEmail(), user.getEmail());
        assertEquals(expectedUserEntity.getPassword(), user.getPassword());
        assertEquals(expectedUserEntity.getRole(), user.getRole());
    }

    @Test
    void shouldFindByUsername_whenValidUsernameIsProvided_thenReturnPersistedUser() throws Exception {
        when(userRepository.findByUsername(mockedUsernameRequest)).thenReturn(Optional.of(expectedUserEntity));

        User user = userOutAdapter.findByUsername(mockedUsernameRequest);

        assertNotNull(user);
        assertEquals(expectedUserEntity.getId(), user.getId());
        assertEquals(expectedUserEntity.getUsername(), user.getUsername());
        assertEquals(expectedUserEntity.getEmail(), user.getEmail());
        assertEquals(expectedUserEntity.getPassword(), user.getPassword());
        assertEquals(expectedUserEntity.getRole(), user.getRole());
    }

    @Test
    void shouldFindById_whenValidIdIsProvided_thenReturnPersistedUser() throws Exception {
        when(userRepository.findById(mockedUserIdRequest)).thenReturn(Optional.of(expectedUserEntity));

        User user = userOutAdapter.findById(mockedUserIdRequest);

        assertNotNull(user);
        assertEquals(expectedUserEntity.getId(), user.getId());
        assertEquals(expectedUserEntity.getUsername(), user.getUsername());
        assertEquals(expectedUserEntity.getEmail(), user.getEmail());
        assertEquals(expectedUserEntity.getPassword(), user.getPassword());
        assertEquals(expectedUserEntity.getRole(), user.getRole());
    }

    @Test
    void shouldFindAll_whenNoParamsAreProvided_thenReturnAllPersistedUsers() throws UserNotFoundException {
        List<User> expectedUsers = mockedUserEntitiesFromDb.stream()
                .map(userEntity -> {
                    try{
                        return UserPersistenceMapper.userEntityToUser(userEntity);
                    } catch (UserNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        when(userRepository.findAll()).thenReturn(mockedUserEntitiesFromDb);

        List<User> users = userOutAdapter.getAll();

        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals(expectedUsers, users);
        assertEquals(expectedUsers.get(0), users.get(0));
        assertEquals(expectedUsers.get(1), users.get(1));
        assertEquals(expectedUsers.get(2), users.get(2));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateUser_whenValidUserRequestIsProvided_thenReturnPersistedUser() throws UserEntityNotFoundException, UserNotFoundException, InvalidUserModel {
        User userFromRequest = UserMapper.userRequestToUser(null, mockedUserRequest);
        userFromRequest.setId(mockedUserIdRequest);

        when(userRepository.save(UserPersistenceMapper.userToUserEntity(userFromRequest))).thenReturn(expectedUserEntity);

        User user = userOutAdapter.updateUser(userFromRequest);

        assertNotNull(user);
        assertEquals(expectedUserEntity.getId(), user.getId());
        assertEquals(expectedUserEntity.getUsername(), user.getUsername());
        assertEquals(expectedUserEntity.getEmail(), user.getEmail());
        assertEquals(expectedUserEntity.getPassword(), user.getPassword());
        assertEquals(expectedUserEntity.getRole(), user.getRole());

        verify(userRepository, times(1)).save(UserPersistenceMapper.userToUserEntity(userFromRequest));
    }

    @Test
    void shouldThrowIllegalArgumentException_whenInvalidUserRequestIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> userOutAdapter.updateUser(null));
    }



    @Test
    void shouldThrowUserEntityNotFoundException_whenInvalidIdIsProvided() {
        assertThrows(UserEntityNotFoundException.class, () -> userOutAdapter.deleteUserById(INVALID_ID));
    }

    @Test
    void shouldThrowIllegalArgumentException_whenInvalidIdIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> userOutAdapter.deleteUserById(null));
    }
}