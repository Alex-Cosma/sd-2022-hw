package com.lab4.demo.quizz.model;

import com.lab4.demo.question.model.Question;
import com.lab4.demo.quizzSession.model.QuizzSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Quizz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Column(length = 512, nullable = false)
    private String title;

    @NotNull(message = "Description cannot be null")
    @Column(length = 512, nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "quizz_question",
            joinColumns = @JoinColumn(name = "quizz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    @Builder.Default
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "quizz",cascade = CascadeType.PERSIST)
    private Collection<QuizzSession> quizzSessions;

}
