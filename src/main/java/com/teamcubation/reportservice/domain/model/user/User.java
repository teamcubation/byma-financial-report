package com.teamcubation.reportservice.domain.model.user;

import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Set<Role> roles;

}
