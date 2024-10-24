package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BonoDto {
    private long id;
    private String name;
    private double price;
    private double interestRate;
    private LocalDate creationDate;
}

