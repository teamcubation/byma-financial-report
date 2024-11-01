package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user;

import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(UserRole roleName);
}
