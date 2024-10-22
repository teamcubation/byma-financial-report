package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user;

import com.teamcubation.reportservice.application.port.out.MockedUserOutPort;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.UserPersistenceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
//@AllArgsConstructor
public class UserOutAdapter implements MockedUserOutPort {

    private  UserRepository userRepository;

    @Override
    public User registerUser(@Valid User user) {

        UserEntity saved = userRepository.save(UserPersistenceMapper.userToUserEntity(user));

        return UserPersistenceMapper.userEntityToUser(saved);
    }

    @Override
    public User findByEmail(String email) throws Exception {
        //TODO implementar custom Exception
        UserEntity userByEmail = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
        return UserPersistenceMapper.userEntityToUser(userByEmail);
    }

    @Override
    public User findByUsername(String username) throws Exception {
        //TODO implementar custom Exception

        UserEntity userByUsername = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));
        return UserPersistenceMapper.userEntityToUser(userByUsername);
    }

    @Override
    public User findById(Long id) throws Exception {
        //TODO implementar custom Exception
        UserEntity userById = userRepository
                .findById(id)
                .orElseThrow(() -> new Exception("User not found"));
        return UserPersistenceMapper.userEntityToUser(userById);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream()
                .map(UserPersistenceMapper::userEntityToUser)
                .toList();
    }

    @Override
    public User updateUser(@Valid User user) {

        validateParams(user);

        UserEntity userEntity = UserPersistenceMapper.userToUserEntity(user);
        UserEntity updated = userRepository.save(userEntity);
        return UserPersistenceMapper.userEntityToUser(updated);
    }

    @Override
    public void deleteUser(Long id) throws UserEntityNotFoundException {
        validateParams(id);
        validateUserExist(id);

        userRepository.deleteById(id);
    }

    private static void validateParams(Object ...params) {
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        for (Object param : params) {
            if (param == null) {
                throw new IllegalArgumentException("Invalid arguments");
            }
        }
    }

    private void validateUserExist(Long id) throws UserEntityNotFoundException {
        if(userIsNotFoundById(id)) {
            throw new UserEntityNotFoundException("User not found");
        }
    }

    private boolean userIsNotFoundById(Long id) {
        return !userRepository.existsById(id);
    }
}
