package com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequestDTO {

    public String username;
    public String email;
    public String password;
}
