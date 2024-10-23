package com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {

    private String username;
    private String email;
    private String password;
    @Builder.Default
    private String role = "USER";
}
