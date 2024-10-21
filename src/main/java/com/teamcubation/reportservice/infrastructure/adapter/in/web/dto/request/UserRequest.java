package com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    public static final String NAME_CANNOT_BE_EMPTY = "The name cannot be empty";
    public static final String EMAIL_CANNOT_BE_EMPTY = "The email cannot be empty";
    public static final String PASSWORD_CANNOT_BE_EMPTY = "The password cannot be empty";
    public static final String ROLE_CANNOT_BE_EMPTY = "The role cannot be empty";

    @NotBlank(message = NAME_CANNOT_BE_EMPTY)
    private String name;

    @NotBlank(message = EMAIL_CANNOT_BE_EMPTY)
    private String email;

    @NotBlank(message = PASSWORD_CANNOT_BE_EMPTY)
    private String password;

    @NotBlank(message = ROLE_CANNOT_BE_EMPTY)
    private String role;





}
