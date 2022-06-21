package com.example.gymapplication.tutorial;

import com.example.gymapplication.BaseControllerTest;
import com.example.gymapplication.TestCreationFactory;
import com.example.gymapplication.training.TrainingController;
import com.example.gymapplication.tutorial.model.Tutorial;
import com.example.gymapplication.tutorial.model.dto.TutorialDTO;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.gymapplication.TestCreationFactory.randomString;
import static com.example.gymapplication.UrlMapping.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TutorialControllerTest extends BaseControllerTest {

    @InjectMocks
    private TutorialController tutorialController;

    @Mock
    private TutorialService tutorialService;

    @Mock
    private TutorialRepository tutorialRepository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        tutorialController = new TutorialController(tutorialService);
        mockMvc = MockMvcBuilders.standaloneSetup(tutorialController).build();
    }

    /*
    @Test
    void addTutorial() throws Exception{
        String title = "image1";
        MultipartFile file = mock(MultipartFile.class);

        tutorialService.addTutorial(title, file);

        ResultActions result = performPostWithRequestPart(TUTORIAL_ADD);

        result.andExpect(status().isOk());
    }
     */

    @Test
    void getAllTutorials() throws Exception{
        List<TutorialDTO> tutorialList = TestCreationFactory.listOf(TutorialDTO.class);
        when(tutorialService.getAllTutorials()).thenReturn(tutorialList);

        ResultActions response = performGet(TUTORIALS+ALL_TUTORIALS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(tutorialList));
    }

    /*
    @Test
    void getTutorialForDownload() throws Exception{
        byte[] imageBytes = {1,2,3,4,5};

        Tutorial tutorial = Tutorial.builder()
                .id(randomString())
                .title("title1")
                .image(new Binary(BsonBinarySubType.BINARY, imageBytes))
                .build();

        //tutorialService.getTutorialForDownload(tutorial.getTitle());
        tutorialRepository.insert(tutorial);

        ResultActions response = performGetWithPathVariable(TUTORIALS+"/"+TUTORIAL_DOWNLOAD, tutorial.getTitle());

        response.andExpect(status().isOk());
    }
    */

}
