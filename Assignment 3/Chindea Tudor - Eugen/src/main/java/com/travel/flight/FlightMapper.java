package com.travel.flight;


import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mappings({
            @Mapping(target = "arrivalCityName", ignore = true),
            @Mapping(target = "departureCityName", ignore = true)
    })
    FlightDTO toDto(Flight flight);

    @Mappings({
            @Mapping(target = "arrivalCity", ignore = true),
            @Mapping(target = "departureCity", ignore = true)
    })
    Flight fromDto(FlightDTO flightDTO);

    @AfterMapping
    default void populateFlightDTOArrivalCityName(Flight flight, @MappingTarget FlightDTO flightDTO){
        flightDTO.setArrivalCityName(flight.getArrivalCity().getName());
    }
    @AfterMapping
    default void populateFlightDTODepartureCityName(Flight flight, @MappingTarget FlightDTO flightDTO){
        flightDTO.setDepartureCityName(flight.getDepartureCity().getName());
    }
}
