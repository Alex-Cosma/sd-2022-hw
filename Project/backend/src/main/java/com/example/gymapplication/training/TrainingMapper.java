package com.example.gymapplication.training;

import com.example.gymapplication.training.model.Training;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import com.example.gymapplication.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

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

    @AfterMapping
    default void populateUsers(Training training, @MappingTarget TrainingDTO trainingDTO) {
        trainingDTO.setUsers(training.getUsers().stream().map(User::getUsername).collect(Collectors.toList()));
    }

    @AfterMapping
    default void populateLocation(Training training, @MappingTarget TrainingDTO trainingDTO) {
        trainingDTO.setLocation(training.getLocation().getAddress());
    }

    @AfterMapping
    default void populateUser(Training training, @MappingTarget TrainingDTO trainingDTO) {
        trainingDTO.setUser(training.getUser().getUsername());
    }


}
