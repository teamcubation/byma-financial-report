package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "bond_seq", sequenceName = "bond_sequence", allocationSize = 1)
    private long id;
    private String name;
    private String email;
    private String password;
    private String role;

}
