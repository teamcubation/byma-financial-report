package com.teamcubation.reportservice.domain.model.user.Rol;

import com.teamcubation.reportservice.domain.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Long id;
    private UserRole role;
}
