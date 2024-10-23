package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
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
public class ConnectionBond {
    @Autowired
    @Qualifier("restTemplateBond")
    private RestTemplate restTemplateBond;

    public List<BonoDto> getAllBonds() {
        String url = "https://bonds-service-latest.onrender.com/api/bonds";
        ResponseEntity<BonoDto[]> response = restTemplateBond.getForEntity(url, BonoDto[].class);
        log.info(Arrays.toString(response.getBody()));
        return Arrays.asList(response.getBody());
    }
}

