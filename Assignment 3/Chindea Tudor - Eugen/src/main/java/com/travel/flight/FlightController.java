package com.travel.flight;

import com.travel.flight.model.dto.FlightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.travel.UrlMapping.FLIGHT;
import static com.travel.UrlMapping.FLIGHT_ID_PART;


@RestController
@RequestMapping(FLIGHT)
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public List<FlightDTO> allFlights(){return flightService.findAll();}

    @PostMapping
    public FlightDTO create(@RequestBody FlightDTO flightDTO) {
        return flightService.create(flightDTO);
    }
    @DeleteMapping(FLIGHT_ID_PART)
    public void delete(@PathVariable Long id){
        flightService.delete(id);
    }

    @PatchMapping(FLIGHT_ID_PART)
    public FlightDTO edit(@PathVariable Long id, @RequestBody FlightDTO flightDTO) {
        return flightService.edit(id, flightDTO);
    }
}
