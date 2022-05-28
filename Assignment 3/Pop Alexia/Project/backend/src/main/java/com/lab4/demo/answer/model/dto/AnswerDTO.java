package com.lab4.demo.answer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private Long id;

    @NotNull(message = "Answer cannot be null")
    private String answer;

    @NotNull
    private Boolean correct;

    private Long questionId;

}
