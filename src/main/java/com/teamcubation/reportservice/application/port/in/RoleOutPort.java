package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.domain.model.user.Rol.Role;

public interface RoleOutPort {
    Role findByRole(String roleName);
}
