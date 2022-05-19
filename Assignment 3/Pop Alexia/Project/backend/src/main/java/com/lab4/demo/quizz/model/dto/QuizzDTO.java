package com.lab4.demo.quizz.model.dto;

import com.lab4.demo.question.model.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizzDTO {

    private Long id;

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull
    private Set<QuestionDTO> questions;

}
