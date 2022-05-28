package com.lab4.demo.answer;

import com.lab4.demo.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findByAnswer(String answerName);

}
