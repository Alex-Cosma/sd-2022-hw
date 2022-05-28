package com.lab4.demo.quizzSession;

import com.lab4.demo.quizzSession.model.QuizzSession;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface QuizzSessionMapper {

    @Mappings({
            @Mapping(target = "userId", source = "quizzSession.user.id"),
            @Mapping(target = "quizzId", source = "quizzSession.quizz.id")
    })
    QuizzSessionDTO toDto(QuizzSession quizzSession);

    @Mappings({
            @Mapping(target = "user.id", source = "quizzSessionDTO.userId"),
            @Mapping(target = "quizz.id", source = "quizzSessionDTO.quizzId")
    })
    QuizzSession fromDto(QuizzSessionDTO quizzSessionDTO);
}