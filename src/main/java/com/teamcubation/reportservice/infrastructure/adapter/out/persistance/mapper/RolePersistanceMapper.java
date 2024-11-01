package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper;

import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.role.RoleEntity;

public class RolePersistanceMapper {

    public static Role roleEntityToRoleModel(RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .role(roleEntity.getRole())
                .build();
    }

    public static RoleEntity roleModelToRoleEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .role(role.getRole())
                .build();
    }
}
