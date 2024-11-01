package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import com.teamcubation.reportservice.domain.model.user.UserRole;

import java.util.Optional;

public interface RoleOutPort {
    Optional<Role> findByRole(UserRole roleName);
    Role create(Role role);
}
