package com.example.gymapplication.training;

import com.example.gymapplication.TestCreationFactory;
import com.example.gymapplication.report.ReportServiceFactory;
import com.example.gymapplication.training.model.Location;
import com.example.gymapplication.training.model.Training;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import com.example.gymapplication.user.UserRepository;
import com.example.gymapplication.user.model.Role;
import com.example.gymapplication.user.model.User;
import com.example.gymapplication.user.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.gymapplication.TestCreationFactory.randomString;
import static com.example.gymapplication.user.model.ERole.TRAINER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TrainingServiceIntegrationTest {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        trainingRepository.deleteAll();
        locationRepository.deleteAll();
        userRepository.deleteAll();
        locationRepository.save(Location.builder()
                .address("Calea Turzii 178")
                .build());

        userRepository.save(User.builder()
                .id(1L)
                .username("trainer")
                .email("email@email.com")
                .password("password")
                .build());
    }

    @Test
    void findAll() {
        List<Training> trainings = TestCreationFactory.listOf(Training.class);
        trainingRepository.saveAll(trainings);

        List<TrainingDTO> trainingDTOS = trainingService.findAll();

        assertEquals(trainings.size(), trainingDTOS.size());
    }

    @Test
    void create() {
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .title(randomString())
                .type(randomString())
                .date(randomString())
                .location("Calea Turzii 178")
                .user("trainer")
                .build();
        trainingDTO = trainingService.create(trainingDTO);

        assertTrue(trainingRepository.findById(trainingDTO.getId()).isPresent());
    }

    @Test
    void delete() {
        TrainingDTO training = TrainingDTO.builder()
                .title(randomString())
                .type(randomString())
                .date(randomString())
                .location("Calea Turzii 178")
                .user("trainer")
                .build();
        TrainingDTO training2 = trainingService.create(training);
        trainingService.delete(training2.getId());

        assertTrue(userRepository.findById(training2.getId()).isEmpty());
    }

    @Test
    void edit() {
        TrainingDTO training = TrainingDTO.builder()
                .title(randomString())
                .type(randomString())
                .date(randomString())
                .location("Calea Turzii 178")
                .user("trainer")
                .build();

        TrainingDTO training2 = trainingService.create(training);
        training2.setTitle("otherTitle");
        trainingService.edit(training2.getId(), training2);

        assertEquals(trainingRepository.findById(training2.getId()).get().getTitle(), training2.getTitle());
    }
}
