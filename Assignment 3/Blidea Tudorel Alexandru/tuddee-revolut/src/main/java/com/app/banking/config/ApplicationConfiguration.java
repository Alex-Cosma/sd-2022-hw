package com.app.banking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Getter
@Setter
@Component
public class ApplicationConfiguration {

    private static final String ENDPOINT = "/latest";

    @Value("${app.client.restTemplate.connectTimeout}")
    private Long connectionTimeout;
    @Value("${app.client.restTemplate.readTimeout}")
    private Long readTimeout;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

}
