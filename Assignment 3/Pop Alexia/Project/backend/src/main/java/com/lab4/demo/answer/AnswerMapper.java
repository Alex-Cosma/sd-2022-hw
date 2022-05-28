package com.lab4.demo.answer;

import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.answer.model.dto.AnswerDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mappings({
            @Mapping(target = "questionId", source = "answer.question.id")
    })
    AnswerDTO toDto(Answer answer);

    @Mappings({
            @Mapping(target = "question.id", source = "answerDTO.questionId")
    })

    Answer fromDto(AnswerDTO answerDTO);

}
