package com.teamcubation.reportservice.infrastructure.adapter.out.externalapi;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiConsumer {
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
