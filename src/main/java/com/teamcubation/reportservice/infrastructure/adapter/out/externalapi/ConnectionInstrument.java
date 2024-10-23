package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConnectionInstrument implements ConnectionOutPort {
    @Autowired
    ConnectionBond connectionBond;
    @Autowired
    ConnectionStock connectionStock;

    @Override
    public List<BonoDto> getAllBonds() {
        return connectionBond.getAllBonds();
    }

    @Override
    public List<StockDto> getAllStocks() {
        return connectionStock.getAllStocks();
    }
}

