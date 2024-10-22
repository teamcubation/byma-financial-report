package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class MockUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
