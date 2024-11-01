package com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.request;

import com.google.gson.Gson;
import com.teamcubation.reportservice.util.JsonConvertible;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest implements JsonConvertible {

    public static final String NAME_CANNOT_BE_EMPTY = "The name cannot be empty";
    public static final String EMAIL_CANNOT_BE_EMPTY = "The email cannot be empty";
    public static final String PASSWORD_CANNOT_BE_EMPTY = "The password cannot be empty";
    public static final String ROLE_CANNOT_BE_EMPTY = "The role cannot be empty";

    private Long id;

    @NotBlank(message = NAME_CANNOT_BE_EMPTY)
    private String username;

    @NotBlank(message = EMAIL_CANNOT_BE_EMPTY)
    private String email;

    @NotBlank(message = PASSWORD_CANNOT_BE_EMPTY)
    private String password;

    @NotEmpty(message = ROLE_CANNOT_BE_EMPTY)
    private Set<String> roles;

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
