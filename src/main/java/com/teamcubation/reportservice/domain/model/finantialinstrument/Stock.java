package com.teamcubation.reportservice.domain.model.finantialinstrument;

import lombok.Data;

@Data
public class Stock extends FinancialInstrument{
    private Double dividend;
}
