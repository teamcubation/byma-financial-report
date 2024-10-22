package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user;

import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.validation.PersistanceValidation;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.UserPersistenceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
public class UserOutAdapter implements UserOutPort {

    private  UserRepository userRepository;

    @Override
    public User registerUser(User user) {

        validateNullParams(user);

        UserEntity saved = userRepository.save(UserPersistenceMapper.userToUserEntity(user));

        return UserPersistenceMapper.userEntityToUser(saved);
    }

    @Override
    public User findByEmailIgnoreCase(String email) throws Exception {

        validateNullParams(email);
        //TODO implementar custom Exception
        UserEntity userByEmail = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new Exception("User not found"));
        return UserPersistenceMapper.userEntityToUser(userByEmail);
    }

    @Override
    public User findByUsername(String username) throws Exception {
        //TODO implementar custom Exception

        validateNullParams(username);

        UserEntity userByUsername = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));
        return UserPersistenceMapper.userEntityToUser(userByUsername);
    }

    @Override
    public User findById(Long id) throws Exception {

        validateNullParams(id);

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
    public User updateUser(User user) {

        validateNullParams(user);

        UserEntity userEntity = UserPersistenceMapper.userToUserEntity(user);
        UserEntity updated = userRepository.save(userEntity);
        return UserPersistenceMapper.userEntityToUser(updated);
    }

    @Override
    public void deleteUserById(Long id) throws UserEntityNotFoundException {
        validateNullParams(id);
        validateUserExist(id);

        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return false;
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return false;
    }

    private static void validateNullParams(Object ...params) {
        if(PersistanceValidation.isNull(params)) {
            throw new IllegalArgumentException("Params cannot be null");
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
