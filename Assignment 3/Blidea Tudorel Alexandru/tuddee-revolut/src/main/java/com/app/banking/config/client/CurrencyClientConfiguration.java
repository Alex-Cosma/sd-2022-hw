package com.app.banking.config.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.client.services.currency")
public class CurrencyClientConfiguration {

    private String baseUrl;
    private String latestRatesEndpoint;
    private String availableCurrenciesEndpoint;

}
