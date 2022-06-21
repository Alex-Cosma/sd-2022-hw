package com.example.gymapplication.tutorial;

import com.example.gymapplication.tutorial.model.Tutorial;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {
    Tutorial findByTitle(String title);
}
