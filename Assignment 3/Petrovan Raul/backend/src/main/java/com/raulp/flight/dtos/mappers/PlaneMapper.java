package com.raulp.flight.dtos.mappers;

import com.raulp.flight.Plane;
import com.raulp.flight.dtos.PlaneDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaneMapper {
    PlaneDTO planetoPlaneDTO(Plane plane);
}
