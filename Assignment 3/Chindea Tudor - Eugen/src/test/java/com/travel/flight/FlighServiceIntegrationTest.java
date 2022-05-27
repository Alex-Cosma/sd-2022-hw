package com.travel.flight;

import com.travel.city.CityMapper;
import com.travel.city.CityRepository;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.travel.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class FlighServiceIntegrationTest {
    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private FlightMapper flightMapper;
    @BeforeEach
    void setUp(){
        flightRepository.deleteAll();
        cityRepository.deleteAll();
    }

    @Test
    void findAll(){
        City departCity = City.builder().id(1L).name("dep").build();
        City arrivCity = City.builder().id(2L).name("ariv").build();
        cityRepository.save(departCity);
        cityRepository.save(arrivCity);
        List<Flight> flights = listOf(Flight.class);
        flights.forEach(flight -> flight.setDepartureCity(departCity));
        flights.forEach(flight -> flight.setArrivalCity(arrivCity));
        flights.forEach(flight -> flight.setSeats(100));
        flights.forEach(flight -> flight.setPrice(100));

        flightRepository.saveAll(flights);
        List<FlightDTO> all = flightService.findAll();
        assertEquals(all.size(),flights.size());
    }
    @Test
    void create(){
        City departCity = City.builder().id(1L).name("dep").build();
        City arrivCity = City.builder().id(2L).name("ariv").build();
        cityRepository.save(departCity);
        cityRepository.save(arrivCity);
        FlightDTO flightDTO = newFlightDTO();
        flightDTO.setArrivalCityName("ariv");
        flightDTO.setDepartureCityName("dep");
        FlightDTO newFlight = flightService.create(flightDTO);
        assertEquals(flightDTO.getArrivalCityName(),newFlight.getArrivalCityName());
    }
    @Test
    void delete(){
        CityDTO arrivDTO = newCityDTO();
        CityDTO departDTO = newCityDTO();
        cityRepository.save(cityMapper.fromDto(arrivDTO));
        cityRepository.save(cityMapper.fromDto(departDTO));

        FlightDTO flightDTO = newFlightDTO();
        flightDTO.setDepartureCityName(departDTO.getName());
        flightDTO.setArrivalCityName(arrivDTO.getName());
        FlightDTO newFlight = flightService.create(flightDTO);
        flightService.delete(newFlight.getId());
        assertFalse(flightRepository.existsById(newFlight.getId()));
    }
    @Test
    void edit(){
        CityDTO arrivDTO = newCityDTO();
        CityDTO departDTO = newCityDTO();
        City arrivCity = cityRepository.save(cityMapper.fromDto(arrivDTO));
        City departCity = cityRepository.save(cityMapper.fromDto(departDTO));
        FlightDTO flightDTO = newFlightDTO();
        flightDTO.setArrivalCityName(arrivCity.getName());
        flightDTO.setDepartureCityName(departCity.getName());
        flightDTO.setPrice(100);
        flightDTO.setSeats(100);
        Flight flight = flightMapper.fromDto(flightDTO);
        flight.setArrivalCity(arrivCity);
        flight.setDepartureCity(departCity);
        Flight newFlight = flightRepository.save(flight);
        flightDTO=flightMapper.toDto(newFlight);
        flightDTO.setDepartureCityName(newFlight.getDepartureCity().getName());
        flightDTO.setArrivalCityName(newFlight.getArrivalCity().getName());
        assertEquals(100,flightDTO.getPrice());
        flightDTO.setPrice(200);
        FlightDTO flightDTO1 = flightService.edit(flightDTO.getId(),flightDTO);
        assertEquals(200,flightDTO1.getPrice());
    }
}
