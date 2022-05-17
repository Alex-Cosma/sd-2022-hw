package com.lab4.demo.quizzSession.model;

import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class QuizzSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quizz_id")
    private Quizz quizz;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "quizz_session_answers",
            joinColumns = @JoinColumn(name = "quizz_session_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id"))
    @Builder.Default
    private Set<Answer> answerSequence = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Integer score;
}
