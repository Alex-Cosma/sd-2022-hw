package com.travel.city;

import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDTO toDto(City city);

    City fromDto(CityDTO cityDTO);
}
