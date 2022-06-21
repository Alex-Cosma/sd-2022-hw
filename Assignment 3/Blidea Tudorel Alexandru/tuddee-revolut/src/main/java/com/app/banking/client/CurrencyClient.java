package com.app.banking.client;

import com.app.banking.config.client.CurrencyClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CurrencyClient {

    private final RestTemplate restTemplate;
    private final CurrencyClientConfiguration currencyClientConfiguration;

    public Map<String, Double> getCurrencies() {
        ResponseEntity<Map<String, Double>> response = restTemplate.exchange(getUrlForLatestRates(), HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Double>>() {
                });
        if (!ObjectUtils.isEmpty(response) && !ObjectUtils.isEmpty(response.getBody())) {
            return response.getBody();
        } else {
            return Map.of();
        }
    }

    public List<String> getAvailableCurrencies() {
        ResponseEntity<List<String>> response = restTemplate.exchange(getUrlForAvailableCurrencies(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {});
        if (!ObjectUtils.isEmpty(response) && !ObjectUtils.isEmpty(response.getBody())) {
            return response.getBody();
        } else {
            return List.of();
        }
    }

    public Double getCurrency(String currency) {
        return getCurrencies().get(currency);
    }

    private String getUrlForLatestRates() {
        return UriComponentsBuilder.fromUri(URI.create(currencyClientConfiguration.getBaseUrl()))
                .pathSegment(currencyClientConfiguration.getLatestRatesEndpoint())
                .encode()
                .build()
                .toString();
    }

    private String getUrlForAvailableCurrencies() {
        return UriComponentsBuilder.fromUri(URI.create(currencyClientConfiguration.getBaseUrl()))
                .pathSegment(currencyClientConfiguration.getAvailableCurrenciesEndpoint())
                .encode()
                .build()
                .toString();
    }

}
