package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user;

import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.MockUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MockUserRepository extends JpaRepository<MockUserEntity, Long> {

    Optional<MockUserEntity> findByUsername(String username);
}

