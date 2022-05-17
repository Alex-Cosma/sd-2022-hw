package com.raulp.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raulp.UrlMapping;
import com.raulp.weather.models.Metar;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlMapping.WEATHER_API)
@RequiredArgsConstructor
public class WeatherApiController {

    private final WeatherService weatherService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(UrlMapping.METAR + "/{icao}")
    @ResponseBody
    public Metar getMetar(@PathVariable String icao) {
        ResponseEntity<?> response = weatherService.getMetarFor(icao);
        if(response.getStatusCode().is2xxSuccessful()) {
            return (Metar) response.getBody();
        }
        return null;
    }
}
