package com.example.gymapplication.training;

import com.example.gymapplication.TestCreationFactory;
import com.example.gymapplication.security.dto.SignupRequest;
import com.example.gymapplication.training.model.Location;
import com.example.gymapplication.training.model.Training;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import com.example.gymapplication.user.UserRepository;
import com.example.gymapplication.user.model.Role;
import com.example.gymapplication.user.model.User;
import com.example.gymapplication.user.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.gymapplication.TestCreationFactory.randomLong;
import static com.example.gymapplication.user.model.ERole.REGULAR_USER;
import static com.example.gymapplication.user.model.ERole.TRAINER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TrainingServiceTest {
    @InjectMocks
    private TrainingService trainingService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingRepository.deleteAll();
        locationRepository.deleteAll();
        userRepository.deleteAll();

        trainingService = new TrainingService(locationRepository, trainingRepository, userRepository, trainingMapper);
    }


    @Test
    void findById() {
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location("Calea Turzii 178")
                .user("trainer")
                .build();

        Training training = Training.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location(Location.builder()
                        .address("Calea Turzii 178")
                        .build())
                .user(User.builder()
                        .email("trainer@trainer.com")
                        .username("trainer")
                        .password("WooHoo1!")
                        .roles(Set.of(new Role(2,TRAINER)))
                        .build())
                .build();

        when(locationRepository.findByAddress("Calea Turzii 178")).thenReturn(Optional.of(Location.builder().address("Calea Turzii 178").build()));
        when(userRepository.findByUsername("trainer")).thenReturn(Optional.of(User.builder().username("trainer").build()));
        when(trainingRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDTO);
        when(trainingMapper.fromDto(trainingDTO)).thenReturn(training);
        when(trainingMapper.toDto(trainingRepository.save(trainingMapper.fromDto(trainingDTO)))).thenReturn(trainingDTO);
        TrainingDTO trainingSaved =  trainingService.create(trainingDTO);

        assertEquals(trainingDTO, trainingService.findById(trainingSaved.getId()));
    }

    @Test
    void findByTitle() {
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location("Calea Turzii 178")
                .user("trainer")
                .build();

        Training training = Training.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location(Location.builder()
                        .address("Calea Turzii 178")
                        .build())
                .user(User.builder()
                        .email("trainer@trainer.com")
                        .username("trainer")
                        .password("WooHoo1!")
                        .roles(Set.of(new Role(2,TRAINER)))
                        .build())
                .build();

        when(locationRepository.findByAddress("Calea Turzii 178")).thenReturn(Optional.of(Location.builder().address("Calea Turzii 178").build()));
        when(userRepository.findByUsername("trainer")).thenReturn(Optional.of(User.builder().username("trainer").build()));
        when(trainingMapper.toDto(training)).thenReturn(trainingDTO);
        when(trainingMapper.fromDto(trainingDTO)).thenReturn(training);
        when(trainingMapper.toDto(trainingRepository.save(trainingMapper.fromDto(trainingDTO)))).thenReturn(trainingDTO);
        trainingService.create(trainingDTO);

        assertEquals(trainingDTO, trainingService.findByTitle("Cardio"));
    }

    @Test
    void findAll() {
        List<Training> trainings = TestCreationFactory.listOf(Training.class);
        when(trainingRepository.findAll()).thenReturn(trainings);

        List<TrainingDTO> all = trainingService.findAll();

        assertEquals(trainings.size(), all.size());
    }

    @Test
    void filterTrainings() {
        TrainingDTO trainingDTO1 = TrainingDTO.builder()
                .id(randomLong())
                .title("title1")
                .type("type1")
                .date("date1")
                .location("Calea Turzii 178")
                .user("trainer")
                .build();

        TrainingDTO trainingDTO2 = TrainingDTO.builder()
                .id(randomLong())
                .title("amazingTitle")
                .type("amazingType")
                .date("amazingDate")
                .location("Calea Turzii 178")
                .user("trainer")
                .build();

        when(locationRepository.findByAddress("Calea Turzii 178")).thenReturn(Optional.of(Location.builder().address("Calea Turzii 178").build()));
        when(userRepository.findByUsername("trainer")).thenReturn(Optional.of(User.builder().username("trainer").build()));

        List<TrainingDTO> filteredTrainings = List.of(trainingDTO2);

        //when(trainingService.create(trainingDTO1)).thenReturn(trainingDTO1);
        when(trainingMapper.toDto(trainingRepository.save(trainingMapper.fromDto(trainingDTO1)))).thenReturn(trainingDTO1);
        when(trainingMapper.toDto(trainingRepository.save(trainingMapper.fromDto(trainingDTO2)))).thenReturn(trainingDTO2);
        //when(trainingService.create(trainingDTO2)).thenReturn(trainingDTO2);
        when(trainingService.filterTrainings("amazing")).thenReturn(filteredTrainings);

        assertEquals(filteredTrainings.get(0), trainingDTO2);
    }

    @Test
    void create() {
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location("Calea Turzii 178")
                .user("trainer")
                .build();

        Training training = Training.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location(Location.builder()
                        .address("Calea Turzii 178")
                        .build())
                .user(User.builder()
                        .email("trainer@trainer.com")
                        .username("trainer")
                        .password("WooHoo1!")
                        .roles(Set.of(new Role(2,TRAINER)))
                        .build())
                .build();

        when(locationRepository.findByAddress("Calea Turzii 178")).thenReturn(Optional.of(Location.builder().address("Calea Turzii 178").build()));
        when(userRepository.findByUsername("trainer")).thenReturn(Optional.of(User.builder().username("trainer").build()));
        when(trainingMapper.toDto(training)).thenReturn(trainingDTO);
        when(trainingMapper.fromDto(trainingDTO)).thenReturn(training);
        when(trainingMapper.toDto(trainingRepository.save(trainingMapper.fromDto(trainingDTO)))).thenReturn(trainingDTO);
        TrainingDTO createdTraining = trainingService.create(trainingDTO);

        assertEquals(createdTraining, trainingMapper.toDto(training));
    }

    @Test
    void delete() {
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location("Calea Turzii 178")
                .user("trainer")
                .build();

        Training training = Training.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location(Location.builder()
                        .address("Calea Turzii 178")
                        .build())
                .user(User.builder()
                        .email("trainer@trainer.com")
                        .username("trainer")
                        .password("WooHoo1!")
                        .roles(Set.of(new Role(2,TRAINER)))
                        .build())
                .build();

        when(locationRepository.findByAddress("Calea Turzii 178")).thenReturn(Optional.of(Location.builder().address("Calea Turzii 178").build()));
        when(userRepository.findByUsername("trainer")).thenReturn(Optional.of(User.builder().username("trainer").build()));
        when(trainingMapper.toDto(training)).thenReturn(trainingDTO);
        when(trainingMapper.fromDto(trainingDTO)).thenReturn(training);
        when(trainingMapper.toDto(trainingRepository.save(trainingMapper.fromDto(trainingDTO)))).thenReturn(trainingDTO);
        trainingService.create(trainingDTO);

        trainingService.delete(1L);
        assertFalse(trainingRepository.findById(1L).isPresent());
    }

    @Test
    void edit() {
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location("Calea Turzii 178")
                .user("trainer")
                .build();

        Training training = Training.builder()
                .id(1L)
                .title("Cardio")
                .type("For teens")
                .date("Monday 19:00")
                .location(Location.builder()
                        .address("Calea Turzii 178")
                        .build())
                .user(User.builder()
                        .email("trainer@trainer.com")
                        .username("trainer")
                        .password("WooHoo1!")
                        .roles(Set.of(new Role(2,TRAINER)))
                        .build())
                .build();

        when(locationRepository.findByAddress("Calea Turzii 178")).thenReturn(Optional.of(Location.builder().address("Calea Turzii 178").build()));
        when(userRepository.findByUsername("trainer")).thenReturn(Optional.of(User.builder().username("trainer").build()));
        when(trainingRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDTO);
        when(trainingMapper.fromDto(trainingDTO)).thenReturn(training);
        when(trainingMapper.toDto(trainingRepository.save(trainingMapper.fromDto(trainingDTO)))).thenReturn(trainingDTO);
        TrainingDTO createdTraining = trainingService.create(trainingDTO);

        createdTraining.setTitle("NewCardio");
        TrainingDTO editedTraining = trainingService.edit(createdTraining.getId(), createdTraining);
        assertEquals("NewCardio", editedTraining.getTitle());
    }
}
