package com.daimler.foodaggregator.config;

import com.daimler.foodaggregator.datastore.FoodInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationBeanConfig {

    @Autowired
    private ApplicationProperties properties;

    @Bean
    WebClient client() {
        return WebClient
                .builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    FoodInventory inventory() {
        return FoodInventory.getInstance();
    }
}
