package com.example.gymapplication.tutorial;

import com.example.gymapplication.tutorial.model.Tutorial;
import com.example.gymapplication.tutorial.model.dto.TutorialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TutorialMapper {
    @Mappings({})
    TutorialDTO toDto(Tutorial tutorial);

    @Mappings({})
    Tutorial fromDto(TutorialDTO tutorial);
}
