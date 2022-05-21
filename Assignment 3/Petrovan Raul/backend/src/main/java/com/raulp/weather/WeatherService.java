package com.raulp.weather;

import com.raulp.UrlMapping;
import com.raulp.weather.models.MetarTaf;
import com.raulp.weather.models.metarDecoded.MetarDecoded;
import com.raulp.weather.models.tafDecoded.TafDecoded;
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
                    MetarTaf.class);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getDecodedMetarFor(String icao) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-api-key", UrlMapping.CHECKWX_API_KEY);
            HttpEntity<Object> entity = new HttpEntity<>(headers);

            String uri = baseUrl + "/metar/" + icao + "/decoded";
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(uri, HttpMethod.GET, entity,
                    MetarDecoded.class);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getTafFor(String icao) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-api-key", UrlMapping.CHECKWX_API_KEY);
            HttpEntity<Object> entity = new HttpEntity<>(headers);

            String uri = baseUrl + "/taf/" + icao;
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(uri, HttpMethod.GET, entity,
                    MetarTaf.class);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getDecodedTafFor(String icao) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-api-key", UrlMapping.CHECKWX_API_KEY);
            HttpEntity<Object> entity = new HttpEntity<>(headers);

            String uri = baseUrl + "/taf/" + icao + "/decoded";
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(uri, HttpMethod.GET, entity,
                    TafDecoded.class);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
