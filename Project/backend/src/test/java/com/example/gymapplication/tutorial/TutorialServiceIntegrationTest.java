package com.example.gymapplication.tutorial;

import com.example.gymapplication.tutorial.model.Tutorial;
import com.example.gymapplication.tutorial.model.dto.TutorialDTO;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TutorialServiceIntegrationTest {
    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private TutorialRepository tutorialRepository;

    @BeforeEach
    void setUp() {
        tutorialRepository.deleteAll();
    }

    @Test
    void getAllTutorials() {
        Tutorial tutorial = Tutorial.builder()
                .id("1")
                .title("Tutorial 1")
                .image(new Binary(BsonBinarySubType.BINARY, "some bytes".getBytes()))
                .build();

        tutorialRepository.save(tutorial);

        List<TutorialDTO> tutorialsDTO = tutorialService.getAllTutorials();

        assertEquals(1, tutorialsDTO.size());
    }
}
