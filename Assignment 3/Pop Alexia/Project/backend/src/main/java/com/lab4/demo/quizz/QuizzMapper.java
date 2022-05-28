package com.lab4.demo.quizz;

import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface QuizzMapper {

    QuizzDTO toDto(Quizz quizz);

    @Mappings({
            @Mapping(target = "quizzSessions" , ignore = true)
    })
    Quizz fromDto(QuizzDTO quizzDTO);

}
