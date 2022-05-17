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

  //  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
   // @JoinTable(name = "question_answers",
            //joinColumns = @JoinColumn(name = "question_id"),
           // inverseJoinColumns = @JoinColumn(name = "answer_id"))
   // @Builder.Default
   // private Set<Answer> answers = new HashSet<>();

    @OneToMany(mappedBy = "question",cascade = CascadeType.MERGE, orphanRemoval = true)
  //  @JsonManagedReference
    private Collection<Answer> answers;

    @NotNull
    @Column(length = 1024)
    private String category;

}
