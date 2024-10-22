package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user;


import com.teamcubation.reportservice.domain.model.user.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;


    private String email;


    private String password;


    @Enumerated(EnumType.STRING)
    private UserRole role;

}
