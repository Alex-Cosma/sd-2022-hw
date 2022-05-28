package com.raulp.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raulp.UrlMapping;
import com.raulp.weather.models.MetarTaf;
import com.raulp.weather.models.metarDecoded.MetarDecoded;
import com.raulp.weather.models.tafDecoded.TafDecoded;
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
    public MetarTaf getMetar(@PathVariable String icao) {
        ResponseEntity<?> response = weatherService.getMetarFor(icao);
        if(response.getStatusCode().is2xxSuccessful()) {
            return (MetarTaf) response.getBody();
        }
        return null;
    }

    @RequestMapping(UrlMapping.METAR + "/{icao}/decoded")
    @ResponseBody
    public MetarDecoded getMetarDecoded(@PathVariable String icao) {
        ResponseEntity<?> response = weatherService.getDecodedMetarFor(icao);
        if(response.getStatusCode().is2xxSuccessful()) {
            return (MetarDecoded) response.getBody();
        }
        return null;
    }

    @RequestMapping(UrlMapping.TAF + "/{icao}")
    @ResponseBody
    public MetarTaf getTaf(@PathVariable String icao) {
        ResponseEntity<?> response = weatherService.getTafFor(icao);
        if(response.getStatusCode().is2xxSuccessful()) {
            return (MetarTaf) response.getBody();
        }
        return null;
    }

    @RequestMapping(UrlMapping.TAF + "/{icao}/decoded")
    @ResponseBody
    public TafDecoded getTafDecoded(@PathVariable String icao) {
        ResponseEntity<?> response = weatherService.getDecodedTafFor(icao);
        if(response.getStatusCode().is2xxSuccessful()) {
            return (TafDecoded) response.getBody();
        }
        return null;
    }
}
