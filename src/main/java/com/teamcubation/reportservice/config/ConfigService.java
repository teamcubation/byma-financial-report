package com.teamcubation.reportservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigService {
    @Bean
    @Qualifier("restTemplateBond")
    public RestTemplate restTemplateBond() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("restTemplateStock")
    public RestTemplate restTemplateStock() {
        return new RestTemplate();
    }

}
