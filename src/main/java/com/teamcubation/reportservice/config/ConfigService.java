package com.teamcubation.reportservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigService {
    public static final String BONDS_SERVICE_URL = "https://bonds-service-latest.onrender.com/api/bonds";
    public static final String STOCKS_SERVICE_URL = "https://stock-service-8pyw.onrender.com/stock/";
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
