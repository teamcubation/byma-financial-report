package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StockDto {
    private long id;
    private String name;
    private double price;
    private double dividend;
    private LocalDate creationDate;
}
