package com.lab4.demo.answer.model;

import com.lab4.demo.question.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Answer cannot be null")
    @Column(length = 1024)
    private String answer;

    @NotNull
    @Column(length = 1024)
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}