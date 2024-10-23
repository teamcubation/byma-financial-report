package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ConnectionBond {
    @Autowired
    @Qualifier("restTemplateBond")
    private RestTemplate restTemplateBond;

    public String getAllBonds() {
        String url = "https://bonds-service-latest.onrender.com/api/bonds";
        ResponseEntity<String> response = restTemplateBond.getForEntity(url, String.class);
        log.info(response.getBody());
        return response.getBody();
    }
}
