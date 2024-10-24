package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import com.teamcubation.reportservice.config.ConfigService;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class ConnectionStock {
    @Autowired
    private ApiConsumer apiConsumer;

    public List<StockDto> getAllStocks() {
        String url = ConfigService.STOCKS_SERVICE_URL;
        ResponseEntity<StockDto[]> response = apiConsumer.restTemplate().getForEntity(url, StockDto[].class);
        log.info(Arrays.toString(response.getBody()));
        return Arrays.asList(response.getBody());
    }
}