package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class ConnectionStock {
    @Autowired
    @Qualifier("restTemplateStock")
    private RestTemplate restTemplateStock;

    public List<StockDto> getAllStocks() {
        String url = "https://stock-service-8pyw.onrender.com/stock/";
        ResponseEntity<StockDto[]> response = restTemplateStock.getForEntity(url, StockDto[].class);
        log.info(Arrays.toString(response.getBody()));
        return Arrays.asList(response.getBody());
    }
}