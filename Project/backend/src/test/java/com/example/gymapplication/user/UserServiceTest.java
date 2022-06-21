package com.example.gymapplication.user;

import com.example.gymapplication.TestCreationFactory;
import com.example.gymapplication.report.ReportServiceFactory;
import com.example.gymapplication.training.LocationRepository;
import com.example.gymapplication.training.TrainingMapper;
import com.example.gymapplication.training.TrainingRepository;
import com.example.gymapplication.training.TrainingService;
import com.example.gymapplication.training.model.Location;
import com.example.gymapplication.training.model.Training;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import com.example.gymapplication.user.model.Role;
import com.example.gymapplication.user.model.User;
import com.example.gymapplication.user.model.dto.UserDTO;
import com.example.gymapplication.user.model.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.gymapplication.TestCreationFactory.randomString;
import static com.example.gymapplication.user.model.ERole.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private TrainingService trainingService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
        roleRepository.deleteAll();
        trainingRepository.deleteAll();
        locationRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,TRAINER));
        roleRepository.save(new Role(3, REGULAR_USER));
        userService = new UserService(userRepository, roleRepository, trainingRepository, userMapper, passwordEncoder);
        trainingService = new TrainingService(locationRepository, trainingRepository, userRepository, trainingMapper, reportServiceFactory);
    }

    @Test
    void allUsersForList() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> all = userService.allUsersForList();

        assertEquals(users.size(), all.size());
    }

    /*
    @Test
    void allTrainers() {
        User user = User.builder()
                .id(1L)
                .username(randomString())
                .password(randomString())
                .roles(Set.of(new Role(2,TRAINER)))
                .build();

        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> all = userService.allTrainers();

        assertEquals(users.size(), all.size());
    }
    */

    @Test
    void findById() {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .role(REGULAR_USER.name())
                .build();
        User user = User.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,REGULAR_USER)))
                .build();

        when(roleRepository.findByName(REGULAR_USER)).thenReturn(Optional.of(Role.builder().name(REGULAR_USER).build()));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(userMapper.toDto(userRepository.save(userMapper.fromDto(userDTO)))).thenReturn(userDTO);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO createdUser = userService.create(userDTO);
        UserDTO foundUser = userService.findById(1L);

        assertEquals(createdUser, foundUser);

    }

    @Test
    void existsByEmail() {
        User user = User.builder()
                .id(1L)
                .username("user1")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .build();

        when(userRepository.save(user)).thenReturn(user);
        userRepository.save(user);

        assertFalse(userService.existsByEmail("email@email1.com"));

    }

    @Test
    void testCreate() {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .role(REGULAR_USER.name())
                .build();
        User user = User.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,REGULAR_USER)))
                .build();

        when(roleRepository.findByName(REGULAR_USER)).thenReturn(Optional.of(Role.builder().name(REGULAR_USER).build()));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(userMapper.toDto(userRepository.save(userMapper.fromDto(userDTO)))).thenReturn(userDTO);
        UserDTO createdUser = userService.create(userDTO);
        assertEquals(createdUser,userMapper.toDto(user));
    }

    @Test
    void testDelete() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .build();

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        userService.delete(1L);
        assertFalse(userService.existsByEmail(user.getEmail()));
    }

    @Test
    void testEdit() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,REGULAR_USER)))
                .build();
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .role(REGULAR_USER.name())
                .build();

        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(roleRepository.findByName(REGULAR_USER)).thenReturn(Optional.of(Role.builder().name(REGULAR_USER).build()));
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        when(userMapper.toDto(userRepository.save(userMapper.fromDto(userDTO)))).thenReturn(userDTO);
        UserDTO createdUser = userService.create(userDTO);

        createdUser.setUsername("newUsername");
        UserDTO editedUser = userService.edit(createdUser.getId(),createdUser);
        assertEquals("newUsername" ,editedUser.getUsername());
    }

    @Test
    void addRegularTraining() {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .role(REGULAR_USER.name())
                .build();
        User user = User.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,REGULAR_USER)))
                .build();

        when(roleRepository.findByName(REGULAR_USER)).thenReturn(Optional.of(Role.builder().name(REGULAR_USER).build()));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        when(userMapper.toDto(userRepository.save(userMapper.fromDto(userDTO)))).thenReturn(userDTO);
        UserDTO createdUser = userService.create(userDTO);

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
                .user(user)
                .build();

        when(locationRepository.findByAddress("Calea Turzii 178")).thenReturn(Optional.of(Location.builder().address("Calea Turzii 178").build()));
        when(userRepository.findByUsername("trainer")).thenReturn(Optional.of(User.builder().username("trainer").build()));
        when(trainingMapper.toDto(training)).thenReturn(trainingDTO);
        when(trainingMapper.fromDto(trainingDTO)).thenReturn(training);
        when(trainingRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(training));
        when(trainingMapper.toDto(trainingRepository.save(trainingMapper.fromDto(trainingDTO)))).thenReturn(trainingDTO);
        TrainingDTO createdTraining = trainingService.create(trainingDTO);

        userService.addRegularTraining(createdUser.getId(),createdTraining.getId());

        assertEquals(createdTraining.getTitle(),user.getRegularTrainings().iterator().next().getTitle());
    }
}
