package com.example.gymapplication.training;

import com.example.gymapplication.training.model.Training;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    @Mappings({
            @Mapping(target = "location", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    TrainingDTO toDto(Training training);

    @Mappings({
            @Mapping(target = "location", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    Training fromDto(TrainingDTO training);


}
