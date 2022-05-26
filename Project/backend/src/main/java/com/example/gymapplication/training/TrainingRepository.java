package com.example.gymapplication.training;

import com.example.gymapplication.training.model.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    Training findByTitle(String title);

    Page<Training> findAllByTitleLikeOrTypeLikeOrDateLike(String title, String type, String date, Pageable pageable);
}
