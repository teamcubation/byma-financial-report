package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;

import java.io.IOException;
import java.util.List;

public interface ConnectionOutPort {
    public List<BonoDto> getAllBonds() throws IOException;
    public List<StockDto> getAllStocks() throws IOException;
}
