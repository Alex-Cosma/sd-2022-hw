package com.example.gymapplication.tutorial;

import com.example.gymapplication.training.TrainingService;
import com.example.gymapplication.tutorial.model.Tutorial;
import com.example.gymapplication.tutorial.model.dto.TutorialDTO;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TutorialServiceTest {
    @InjectMocks
    private TutorialService tutorialService;

    @Mock
    private TutorialRepository tutorialRepository;

    @Mock
    private TutorialMapper tutorialMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tutorialRepository.deleteAll();

        tutorialService = new TutorialService(tutorialRepository, tutorialMapper);
    }


    @Test
    void addTutorial() throws Exception {
        String title = "file.png";
        MultipartFile file = new MockMultipartFile("file.png", "file.png", "image/png", "some bytes".getBytes());

        tutorialService.addTutorial(title, file);
        Tutorial tutorialToBeFound = tutorialRepository.findByTitle(title);

        assertEquals(title, "file.png");
    }

    @Test
    void getAllTutorials() throws Exception {
        Tutorial tutorial = Tutorial.builder()
                .id("1")
                .title("title")
                .image(new Binary(BsonBinarySubType.BINARY, "some bytes".getBytes()))
                .build();

        TutorialDTO tutorialDTO = TutorialDTO.builder()
                .id("1")
                .title("title")
                .image(new Binary(BsonBinarySubType.BINARY, "some bytes".getBytes()))
                .build();

        when(tutorialMapper.toDto(tutorial)).thenReturn(tutorialDTO);
        when(tutorialMapper.fromDto(tutorialDTO)).thenReturn(tutorial);
        when(tutorialMapper.toDto(tutorialRepository.insert(tutorialMapper.fromDto(tutorialDTO)))).thenReturn(tutorialDTO);

        List<Tutorial> tutorialList = new ArrayList<>();
        tutorialList.add(tutorial);

        when(tutorialRepository.findAll()).thenReturn(tutorialList);

        List<TutorialDTO> tutorials = tutorialService.getAllTutorials();

        assertEquals(1, tutorialList.size());
    }

    /*
    @Test
    void getTutorialForDownload() {
        Tutorial tutorial = Tutorial.builder()
                .id("1")
                .title("title")
                .image(new Binary(BsonBinarySubType.BINARY, "some bytes".getBytes()))
                .build();

        when(tutorialRepository.insert(tutorial)).thenReturn(tutorial);

        byte[] imageBytes = tutorialService.getTutorialForDownload(tutorial.getTitle());

        assertEquals(imageBytes, "some bytes".getBytes());
    }
    */

}
