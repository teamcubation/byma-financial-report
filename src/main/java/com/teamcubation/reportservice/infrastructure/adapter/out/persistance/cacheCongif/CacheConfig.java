package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.cacheCongif;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("allReports"),
                new ConcurrentMapCache("reportsByUserEmail"),
                new ConcurrentMapCache("reports")
        ));
        return cacheManager;
    }
}
