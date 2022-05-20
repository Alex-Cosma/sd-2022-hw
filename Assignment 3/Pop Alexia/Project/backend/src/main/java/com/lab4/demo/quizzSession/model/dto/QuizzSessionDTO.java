package com.lab4.demo.quizzSession.model.dto;

import com.lab4.demo.answer.model.dto.AnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizzSessionDTO {

    private Long id;
    private Long quizzId;
    private Set<AnswerDTO> answerSequence;
    private Integer score;
    private Long userId;

}
