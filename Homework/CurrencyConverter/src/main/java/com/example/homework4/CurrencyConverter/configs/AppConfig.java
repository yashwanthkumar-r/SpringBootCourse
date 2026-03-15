package com.example.homework4.CurrencyConverter.configs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class AppConfig {

    @Value("${currencyService.base.url}")
    private String BASE_URL;

    @Bean
    RestClient getCurrencyService() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
