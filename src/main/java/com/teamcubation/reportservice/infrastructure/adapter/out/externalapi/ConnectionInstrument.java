package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionInstrument implements ConnectionOutPort {
    @Autowired
    ConnectionBond connectionBond;
    @Autowired
    ConnectionStock connectionStock;

    @Override
    public String getAll() {
        
        return connectionBond.getAllBonds() + connectionStock.getAllStocks();
    }
}
