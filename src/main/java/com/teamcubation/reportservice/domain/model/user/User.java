package com.teamcubation.reportservice.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;

}
