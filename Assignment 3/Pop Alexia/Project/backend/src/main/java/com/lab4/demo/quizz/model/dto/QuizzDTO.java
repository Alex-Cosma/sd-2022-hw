package com.lab4.demo.quizz.model.dto;

import com.lab4.demo.question.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizzDTO {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Set<Question> questions;

    @NotNull
    private Integer points;


    public String toString(){
        return id + "," + title + ","+ description +"," +points +"," + questions.stream().map(Question::toString).collect(Collectors.toList()) +"\n";
    }
}
