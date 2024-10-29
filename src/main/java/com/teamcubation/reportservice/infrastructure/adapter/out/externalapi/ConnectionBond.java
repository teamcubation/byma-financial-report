package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import com.teamcubation.reportservice.config.ConfigService;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
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
public class ConnectionBond {
    @Autowired
    private ApiConsumer apiConsumer;

    public List<BonoDto> getAllBonds() {
        String url = ConfigService.BONDS_SERVICE_URL;
        ResponseEntity<BonoDto[]> response = apiConsumer.restTemplate().getForEntity(url, BonoDto[].class);
        log.info(Arrays.toString(response.getBody()));
        return Arrays.asList(response.getBody());
    }
}

