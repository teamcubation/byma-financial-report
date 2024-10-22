package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.ConnectionBond;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.ConnectionStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {
    private final ConnectionBond connectionBond;
    private final ConnectionStock connectionStock;


    public String getAllBonds() {
        return connectionBond.getAllBonds();
    }

    public String getAllStocks() {
        return connectionStock.getAllStocks();
    }
}
