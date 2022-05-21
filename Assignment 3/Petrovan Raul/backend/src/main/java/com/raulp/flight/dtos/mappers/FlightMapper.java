package com.raulp.flight.dtos.mappers;

import com.raulp.flight.Flight;
import com.raulp.flight.dtos.FlightDTO;
import com.raulp.user.mapper.InstructorMapper;
import com.raulp.user.mapper.StudentMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {InstructorMapper.class, StudentMapper.class})
public interface FlightMapper {
    FlightDTO flightToFlightDTO(Flight flight);
}
