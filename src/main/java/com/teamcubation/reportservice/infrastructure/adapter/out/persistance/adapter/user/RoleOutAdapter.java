package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user;

import com.teamcubation.reportservice.application.port.in.RoleOutPort;
import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.role.RoleEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.RolePersistanceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleOutAdapter implements RoleOutPort {


    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findByRole(UserRole roleName) {

        Optional<RoleEntity> roleEntity = roleRepository.findByRole(roleName);

        return roleEntity.map(RolePersistanceMapper::roleEntityToRoleModel);
    }

    @Override
    public Role create(Role role) {

        RoleEntity roleEntity = RolePersistanceMapper.roleModelToRoleEntity(role);
        RoleEntity savedRole = roleRepository.save(roleEntity);
        return RolePersistanceMapper.roleEntityToRoleModel(savedRole);
    }
}
