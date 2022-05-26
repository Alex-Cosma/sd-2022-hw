package com.policeSection.fine;

import com.policeSection.fine.Model.Fine;
import com.policeSection.fine.Model.dto.FineDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FineMapper {
    FineDTO toDto(Fine fine);

    Fine fromDto(FineDTO fine);

}
