package com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response;

import com.teamcubation.reportservice.domain.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

        private String name;
        private String email;
        private String password;
        private UserRole role;

}
