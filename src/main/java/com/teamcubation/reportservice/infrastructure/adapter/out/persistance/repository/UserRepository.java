package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository;

import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
}
