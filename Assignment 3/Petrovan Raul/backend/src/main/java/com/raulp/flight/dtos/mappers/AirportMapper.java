package com.raulp.flight.dtos.mappers;

import com.raulp.flight.Airport;
import com.raulp.flight.dtos.AirportDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportDTO airportToAirportDTO(Airport airport);
}
