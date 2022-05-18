package com.lab4.demo.question;

import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDTO toDto(Question question);
    Question fromDto(QuestionDTO questionDTO);

}
