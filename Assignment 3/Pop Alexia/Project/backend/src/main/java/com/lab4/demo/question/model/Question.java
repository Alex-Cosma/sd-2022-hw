package com.lab4.demo.question.model;

import com.lab4.demo.answer.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 512, nullable = false)
    private String statement;

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER, orphanRemoval = true)
    private Collection<Answer> answers;

    @NotNull
    @Column(length = 1024)
    private String category;

}
