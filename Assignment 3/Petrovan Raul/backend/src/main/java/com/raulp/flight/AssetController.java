package com.raulp.flight;

import com.raulp.UrlMapping;
import com.raulp.flight.dtos.AirportDTO;
import com.raulp.flight.dtos.PlaneDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.API_PATH)
@RequiredArgsConstructor
public class AssetController {
    private final FlightService flightService;

    @GetMapping(UrlMapping.AIRPLANES)
    public List<PlaneDTO> getAirplanes() {
        return flightService.getAirplanes();
    }

    @GetMapping(UrlMapping.AIRPORTS)
    public List<AirportDTO> getAirports() {
        return flightService.getAirports();
    }
}
