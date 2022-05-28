package com.lab4.demo.quizz;

import com.lab4.demo.quizz.model.Quizz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Long> {
    Quizz findByTitle(String title);

    Page<Quizz> findAllByTitleLikeOrDescriptionLike(String title,String description, Pageable pageable);
}
