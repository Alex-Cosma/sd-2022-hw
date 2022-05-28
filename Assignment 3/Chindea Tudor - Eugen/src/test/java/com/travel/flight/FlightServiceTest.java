package com.travel.flight;

import com.travel.TestCreationFactory;
import com.travel.city.CityRepository;
import com.travel.city.model.City;
import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import com.travel.hotel.HotelService;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.travel.TestCreationFactory.*;
import static com.travel.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightMapper flightMapper;

    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flightService = new FlightService(flightRepository,cityRepository,flightMapper);
    }

    @Test
    void findAll() {
        List<Flight> flights = TestCreationFactory.listOf(Flight.class);
        when(flightRepository.findAll()).thenReturn(flights);

        List<FlightDTO> all = flightService.findAll();

        Assertions.assertEquals(flights.size(), all.size());
    }

    @Test
    void create() {
        Flight flight = newFlight();
        FlightDTO flightDTO = newFlightDTO();
        City departCity = newCity();
        String departCityName = randomString();
        departCity.setName(departCityName);
        flight.setDepartureCity(departCity);
        City arrivCity = newCity();
        String arrivCityName = randomString();
        arrivCity.setName(arrivCityName);
        flight.setArrivalCity(arrivCity);
        flightDTO.setArrivalCityName(arrivCityName);
        flightDTO.setDepartureCityName(departCityName);
        when(flightMapper.toDto(flight)).thenReturn(flightDTO);
        when(flightMapper.fromDto(flightDTO)).thenReturn(flight);
        when(flightRepository.save(flight)).thenReturn(flight);
        when(cityRepository.findByName(departCityName)).thenReturn(Optional.of(departCity));
        when(cityRepository.findByName(arrivCityName)).thenReturn(Optional.of(arrivCity));
        FlightDTO newFlightDTO = flightService.create(flightDTO);

        assertEquals(newFlightDTO,flightDTO);
    }

    @Test
    void delete() {
        City departCity = newCity();
        when(cityRepository.save(departCity)).thenReturn(departCity);
        City arrivCity = newCity();
        when(cityRepository.save(arrivCity)).thenReturn(arrivCity);
        Flight flight = newFlight();
        when(flightRepository.save(flight)).thenReturn(flight);
        flight.setArrivalCity(arrivCity);
        flight.setDepartureCity(departCity);
        Long id = flight.getId();
        when(flightRepository.findById(id)).thenReturn(Optional.of(flight));
        doNothing().when(flightRepository).delete(flight);
        flightService.delete(flight.getId());
        assertFalse(flightRepository.existsById(id));
    }

    @Test
    void edit() {
        Long flightid = randomLong();
        Flight flight = newFlight();
        FlightDTO flightDTO = newFlightDTO();
        City departCity = newCity();
        String departCityName = randomString();
        departCity.setName(departCityName);
        flight.setDepartureCity(departCity);
        City arrivCity = newCity();
        String arrivCityName = randomString();
        arrivCity.setName(arrivCityName);
        flight.setArrivalCity(arrivCity);
        flight.setId(flightid);
        flightDTO.setDepartureCityName(departCity.getName());
        flightDTO.setArrivalCityName(arrivCity.getName());
        when(flightRepository.findById(flightid)).thenReturn(Optional.of(flight));
        when(flightMapper.toDto(flight)).thenReturn(flightDTO);
        when(flightMapper.fromDto(flightDTO)).thenReturn(flight);
        when(flightRepository.save(flight)).thenReturn(flight);
        when(cityRepository.findByName(departCityName)).thenReturn(Optional.of(departCity));
        when(cityRepository.findByName(arrivCityName)).thenReturn(Optional.of(arrivCity));
        FlightDTO newflightDTO = flightService.edit(flightid,flightDTO);

        assertEquals(newflightDTO,flightDTO);
    }
}
