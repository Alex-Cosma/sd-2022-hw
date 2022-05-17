package com.raulp.weather;

import com.raulp.UrlMapping;
import com.raulp.weather.models.Metar;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {
    String baseUrl = UrlMapping.CHECKWX_URL;

    public ResponseEntity<?> getMetarFor(String icao) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-api-key", UrlMapping.CHECKWX_API_KEY);
            HttpEntity<Object> entity = new HttpEntity<>(headers);

            String uri = baseUrl + "/metar/" + icao;
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(uri, HttpMethod.GET, entity,
                    Metar.class);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
