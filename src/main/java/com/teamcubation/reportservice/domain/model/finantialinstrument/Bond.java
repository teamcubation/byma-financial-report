package com.teamcubation.reportservice.domain.model.finantialinstrument;

import lombok.Data;

@Data
public class Bond extends FinancialInstrument {
    private Double interestRate;
}
