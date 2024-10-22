package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConnectionStock {
    @Autowired
    @Qualifier("restTemplateStock")
    private RestTemplate restTemplateStock;

    public String getAllStocks() {
        String url = "https://stock-service-8pyw.onrender.com/stock/";
        ResponseEntity<String> response = restTemplateStock.getForEntity(url, String.class);
        return response.getBody();
    }
}