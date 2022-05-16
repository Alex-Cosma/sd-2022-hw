package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {

    AccommodationDTO toDto(Accommodation accommodation);

    Accommodation fromDto(AccommodationDTO accommodationDTO);
}
