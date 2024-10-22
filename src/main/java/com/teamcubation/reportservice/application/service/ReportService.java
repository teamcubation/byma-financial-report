package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.ConnectionBond;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.ConnectionStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {
    private final ConnectionOutPort connectionOutPort;

    public String getAll() {
        return connectionOutPort.getAll();
    }

}
