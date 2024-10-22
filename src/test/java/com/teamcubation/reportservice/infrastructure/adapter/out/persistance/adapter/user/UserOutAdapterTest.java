package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user;

import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request.UserRequest;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.mapper.UserMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.UserPersistenceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserOutAdapterTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserOutAdapter userOutAdapter;

    private UserRequest mockedUserRequest;

    private String mockedEmailRequest;

    private String mockedUsernameRequest;

    private Long mockedUserIdRequest;

    private List<UserEntity> mockedUserEntitiesFromDb;

    @BeforeEach
    public void setUp() {
        mockedUserRequest = UserRequest.builder()
                .username("username")
                .email("email")
                .password("password")
                .role("ROLE_ADMIN")
                .build();

        mockedEmailRequest = "test@gmail.com";

        mockedUsernameRequest = "username";

        mockedUserIdRequest = 1L;

        mockedUserEntitiesFromDb = List.of(
                UserEntity.builder()
                        .id(1L)
                        .username("Juan")
                        .email("test@gmail.com")
                        .password("password")
                        .role(UserRole.ROLE_ADMIN)
                        .build(),
                UserEntity.builder()
                        .id(2L)
                        .username("Pepe")
                        .email("test2@gmail.com")
                        .password("password")
                        .role(UserRole.ROLE_ADMIN)
                        .build(),
                UserEntity.builder()
                        .id(3L)
                        .username("Jhon")
                        .email("test3@gmail.com")
                        .password("password")
                        .role(UserRole.ROLE_ADMIN)
                        .build()
        );

    }

    @Test
    public void shouldRegisterUser_whenValidUserIsProvided_thenReturnPersistedUser() {
        UserEntity expectedUserEntity = UserEntity.builder()
                .id(1L)
                .username("username")
                .email("test@gmail.com")
                .password("password")
                .role(UserRole.ROLE_ADMIN)
                .build();
        User userFromRequest = UserMapper.userRequestToUser(mockedUserRequest);

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
        UserEntity expectedUserEntity = UserEntity.builder()
                .id(1L)
                .username("username")
                .email("test@gmail.com")
                .password("password")
                .role(UserRole.ROLE_ADMIN)
                .build();

        when(userRepository.findByEmail(mockedEmailRequest)).thenReturn(Optional.of(expectedUserEntity));

        User user = userOutAdapter.findByEmail(mockedEmailRequest);

        assertNotNull(user, "User should not be null");
        assertEquals(expectedUserEntity.getId(), user.getId(), "Id should be equal");
        assertEquals(expectedUserEntity.getUsername(), user.getUsername(), "Username should be equal");
        assertEquals(expectedUserEntity.getEmail(), user.getEmail(), "Email should be equal");
        assertEquals(expectedUserEntity.getPassword(), user.getPassword(), "Password should be equal");
        assertEquals(expectedUserEntity.getRole(), user.getRole(), "Role should be equal");
    }

    @Test
    void shouldFindByUsername_whenValidUsernameIsProvided_thenReturnPersistedUser() throws Exception {
        UserEntity expectedUserEntity = UserEntity.builder()
                .id(1L)
                .username("username")
                .email("test@gmail.com")
                .password("password")
                .role(UserRole.ROLE_ADMIN)
                .build();

        when(userRepository.findByUsername(mockedUsernameRequest)).thenReturn(Optional.of(expectedUserEntity));

        User user = userOutAdapter.findByUsername(mockedUsernameRequest);

        assertNotNull(user, "User should not be null");
        assertEquals(expectedUserEntity.getId(), user.getId(), "Id should be equal");
        assertEquals(expectedUserEntity.getUsername(), user.getUsername(), "Username should be equal");
        assertEquals(expectedUserEntity.getEmail(), user.getEmail(), "Email should be equal");
        assertEquals(expectedUserEntity.getPassword(), user.getPassword(), "Password should be equal");
        assertEquals(expectedUserEntity.getRole(), user.getRole(), "Role should be equal");
    }

    @Test
    void shouldFindById_whenValidIdIsProvided_thenReturnPersistedUser() throws Exception {
        UserEntity expectedUserEntity = UserEntity.builder()
                .id(1L)
                .username("username")
                .email("test@gmail.com")
                .password("password")
                .role(UserRole.ROLE_ADMIN)
                .build();

        when(userRepository.findById(mockedUserIdRequest)).thenReturn(Optional.of(expectedUserEntity));

        User user = userOutAdapter.findById(mockedUserIdRequest);

        assertNotNull(user, "User should not be null");
        assertEquals(expectedUserEntity.getId(), user.getId(), "Id should be equal");
        assertEquals(expectedUserEntity.getUsername(), user.getUsername(), "Username should be equal");
        assertEquals(expectedUserEntity.getEmail(), user.getEmail(), "Email should be equal");
        assertEquals(expectedUserEntity.getPassword(), user.getPassword(), "Password should be equal");
        assertEquals(expectedUserEntity.getRole(), user.getRole(), "Role should be equal");
    }

    @Test
    void shouldFindAll_whenNoParamsAreProvided_thenReturnAllPersistedUsers() {

        List<User> expectedUsers = mockedUserEntitiesFromDb.stream()
                .map(UserPersistenceMapper::userEntityToUser)
                .toList();

        when(userRepository.findAll()).thenReturn(mockedUserEntitiesFromDb);

        List<User> users = userOutAdapter.getAll();

        assertNotNull(users);
        assertEquals(3, users.size(), "Size should be equal to 3");
        assertEquals(expectedUsers, users, "Users should be equal");
        assertEquals(expectedUsers.get(0), users.get(0), "Users should be equal");
        assertEquals(expectedUsers.get(1), users.get(1), "Users should be equal");
        assertEquals(expectedUsers.get(2), users.get(2), "Users should be equal");

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateUser_whenValidUserRequestIsProvided_thenReturnPersistedUser() {
        UserEntity expectedUserEntity = UserEntity.builder()
                .id(1L)
                .username("username")
                .email("test@gmail.com")
                .password("password")
                .role(UserRole.ROLE_ADMIN)
                .build();



        User userFromRequest = UserMapper.userRequestToUser(mockedUserRequest);
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
    void shouldThrowException_wheInvalidEmailIsProvided() {

        User user = UserMapper.userRequestToUser(mockedUserRequest);
        user.setId(mockedUserIdRequest);
        user.setEmail("test");

        userOutAdapter.updateUser(user);
    }


    //Delete tests

    @Test
    void shouldDeleteUser_whenValidIdIsProvided() throws UserEntityNotFoundException {

        userOutAdapter.deleteUser(mockedUserIdRequest);

        verify(userRepository, times(1)).deleteById(mockedUserIdRequest);
    }

    @Test
    void shouldThrowUserEntityNotFoundException_whenInvalidIdIsProvided() {
        assertThrows(UserEntityNotFoundException.class, () -> userOutAdapter.deleteUser(-1L));
    }

    @Test
    void shouldThrowIllegalArgumentException_whenInvalidIdIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> userOutAdapter.deleteUser(null));
    }
}