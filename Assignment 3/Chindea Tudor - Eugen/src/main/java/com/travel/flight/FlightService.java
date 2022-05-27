package com.travel.flight;

import com.travel.city.CityMapper;
import com.travel.city.CityRepository;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final CityRepository cityRepository;
    private final FlightMapper flightMapper;
    public List<FlightDTO> findAll(){
        List<FlightDTO> allFlightsDTO = flightRepository.findAll().stream()
                .map(flightMapper::toDto)
                .collect(Collectors.toList());
        List<Flight> allFlights = flightRepository.findAll();
        for(int i = 0; i < allFlights.size();i++){
            flightMapper.populateFlightDTOArrivalCityName(allFlights.get(i),allFlightsDTO.get(i));
            flightMapper.populateFlightDTODepartureCityName(allFlights.get(i),allFlightsDTO.get(i));
        }
        return allFlightsDTO;
    }

    private Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found: " + id));
    }

    public FlightDTO create(FlightDTO flightDTO){

        City departureCity = cityRepository.findByName(flightDTO.getDepartureCityName()).get();
        City arrivalCity = cityRepository.findByName(flightDTO.getArrivalCityName()).get();
        Flight flight = flightMapper.fromDto(flightDTO);
        flight.setDepartureCity(departureCity);
        flight.setArrivalCity(arrivalCity);
        FlightDTO flightDTO1 = flightMapper.toDto(flightRepository.save(flight));
        flightMapper.populateFlightDTOArrivalCityName(flight,flightDTO1);
        flightMapper.populateFlightDTODepartureCityName(flight,flightDTO1);
        return flightDTO1;
    }

    public void delete(Long id){
        Flight flight = findById(id);
        flightRepository.delete(flight);
    }

    public FlightDTO edit(Long id, FlightDTO flightDTO) {
        Flight actFlight = findById(id);
        actFlight.setSeats(flightDTO.getSeats());
        actFlight.setPrice(flightDTO.getPrice());
        City departureCity = cityRepository.findByName(flightDTO.getDepartureCityName()).get();
        City arrivalCity = cityRepository.findByName(flightDTO.getArrivalCityName()).get();
        actFlight.setArrivalCity(arrivalCity);
        actFlight.setDepartureCity(departureCity);

        FlightDTO flightDTO1 = flightMapper.toDto(flightRepository.save(actFlight));
        flightMapper.populateFlightDTOArrivalCityName(actFlight,flightDTO1);
        flightMapper.populateFlightDTODepartureCityName(actFlight,flightDTO1);
        return flightDTO1;

    }
}
