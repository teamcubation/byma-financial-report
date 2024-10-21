package com.teamcubation.reportservice.domain.model.finantialinstrument;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FinancialInstrument {
    private long id;
    private String name;
    private Double price;
    private LocalDate creationDate;
}
