package com.lab4.demo.quizzSession.model.dto;

import com.lab4.demo.answer.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizzSessionDTO {

    private Long id;
    private Long quizzId;
    private Set<Answer> answerSequence;
    private Integer score;
    private Long userId;

    public String toString(){
        return id + "," + quizzId +"," + answerSequence.stream().map(Answer::toString).collect(Collectors.toList()) +","+ score ;
    }
}
