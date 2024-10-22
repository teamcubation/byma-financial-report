package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository;

import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
