package com.lab4.demo.question;

import com.lab4.demo.question.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByStatement(String statement);

    Page<Question> findAllByCategoryLikeOrStatementLike(String category,String statement, Pageable pageable);
}
