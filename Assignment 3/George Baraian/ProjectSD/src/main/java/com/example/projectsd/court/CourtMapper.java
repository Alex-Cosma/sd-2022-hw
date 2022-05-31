package com.example.projectsd.court;

import com.example.projectsd.court.model.Court;
import com.example.projectsd.court.model.dto.CourtDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourtMapper {

    CourtDTO toDto(Court sport);

    Court fromDto(CourtDTO sport);

}
