package com.example.gymapplication.training;

import com.example.gymapplication.training.model.Location;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import com.example.gymapplication.user.UserRepository;
import com.example.gymapplication.user.model.Role;
import com.example.gymapplication.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.gymapplication.TestCreationFactory.randomString;
import static com.example.gymapplication.user.model.ERole.TRAINER;
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
    void findById() {
    }

    @Test
    void findByTitle() {
    }

    @Test
    void findAll() {
    }

    @Test
    void filterTrainings() {
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
    }

    @Test
    void edit() {
    }
}
