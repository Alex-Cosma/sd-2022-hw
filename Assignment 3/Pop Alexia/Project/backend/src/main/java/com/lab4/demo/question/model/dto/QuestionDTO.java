package com.lab4.demo.question.model.dto;

import com.lab4.demo.answer.model.dto.AnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long id;

    @NotNull(message = "Question cannot be null")
    private String statement;

    @NotNull
    private Collection<AnswerDTO> answers;

    @NotNull(message = "Category cannot be null")
    private String category;

}
