package com.lab4.demo.question.model.dto;

import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.question.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long id;

    @NotNull
    private String statement;

    @NotNull
    private Set<Answer> answers;

    @NotNull
    private String category;

   public String toString(){
        return id + "," + statement + "," + answers.stream().map(Answer::toString).collect(Collectors.toList()) + ","+ category + "\n";
    }
}
