package com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {

    @NotBlank(message = "The username cannot be empty")
    private String username;
    @Email(message = "The email is not valid")
    @NotBlank(message = "The email cannot be empty")
    private String email;
    @NotBlank(message = "The password cannot be empty")
    private String password;
}
