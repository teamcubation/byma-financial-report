package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.ReportInPort;
import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;

import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService implements ReportInPort {
    private final ConnectionOutPort connectionOutPort;

    public byte[] generatePdf() throws IOException {
        List<BonoDto> allBonds = connectionOutPort.getAllBonds();
        List<StockDto> allStocks = connectionOutPort.getAllStocks();

        return GeneratorPdf.generatePdfContent(allBonds, allStocks);
    }

    public byte[] generateCsv() throws IOException {
        List<BonoDto> allBonds = connectionOutPort.getAllBonds();
        List<StockDto> allStocks = connectionOutPort.getAllStocks();

        return GeneratorCsv.generateCsv(allBonds, allStocks);
    }
}

