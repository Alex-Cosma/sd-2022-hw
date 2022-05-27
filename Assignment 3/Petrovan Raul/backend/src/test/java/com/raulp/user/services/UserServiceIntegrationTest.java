package com.raulp.user.services;

import com.raulp.user.dto.user.UserDetailsDTO;
import com.raulp.user.dto.user.UserListDTO;
import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.mapper.UserMapper;
import com.raulp.user.model.ERole;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Role;
import com.raulp.user.model.Student;
import com.raulp.user.model.User;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.RoleRepository;
import com.raulp.user.repos.StudentRepository;
import com.raulp.user.repos.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for (int i = 0; i < nrUsers; i++) {
            int finalI = i;
            assertEquals(users.get(i).getUsername(),
                    userMinimalDTOS.stream()
                            .filter(user -> user.getId() == users.get(finalI).getId()).collect(
                                    Collectors.toList()).get(0).getName());
        }
    }

    @Test
    void allUsersForList() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserListDTO> userListDTOS = userService.allUsersForList();

        for (int i = 0; i < nrUsers; i++) {
            int finalI = i;
            assertEquals(users.get(i).getUsername(),
                    userListDTOS.stream()
                            .filter(user -> user.getId() == users.get(finalI).getId()).collect(
                                    Collectors.toList()).get(0).getName());
        }
    }

    @Test
    void myDetails() {
        User user = User.builder()
                .username("User")
                .password(UUID.randomUUID().toString())
                .email("user@gmail.com")
                .build();
        user = userRepository.save(user);

        Student student = Student.builder()
                .username("Student")
                .password(UUID.randomUUID().toString())
                .email("Student" + "@gmail.com")
                .build();
        student = studentRepository.save(student);

        Instructor instructor = Instructor.builder()
                .username("Instructor")
                .password(UUID.randomUUID().toString())
                .email("Instructor" + "@gmail.com")
                .build();
        instructor = instructorRepository.save(instructor);

        assertNotEquals(user.getUsername(), userService.myDetails(user.getId()).getFirstName());
        assertEquals(student.getFirstName(), userService.myDetails(student.getId()).getFirstName());
        assertEquals(instructor.getFirstName(),
                userService.myDetails(instructor.getId()).getFirstName());
    }

    @Test
    void updateUser() {
        User user = User.builder()
                .username("User")
                .password(UUID.randomUUID().toString())
                .email("user@gmail.com")
                .build();
        user = userRepository.save(user);

        Student student = Student.builder()
                .username("Student")
                .password(UUID.randomUUID().toString())
                .email("Student" + "@gmail.com")
                .build();
        student = studentRepository.save(student);

        Instructor instructor = Instructor.builder()
                .username("Instructor")
                .password(UUID.randomUUID().toString())
                .email("Instructor" + "@gmail.com")
                .build();
        instructor = instructorRepository.save(instructor);

        UserDetailsDTO userDetailsDTO = UserDetailsDTO.builder()
                .firstName("First")
                .lastName("Last")
                .build();

        userService.updateUser(userDetailsDTO, user.getId());
        userService.updateUser(userDetailsDTO, student.getId());
        userService.updateUser(userDetailsDTO, instructor.getId());

        assertEquals(instructorRepository.findById(instructor.getId()).get().getFirstName(),
                userService.myDetails(instructor.getId()).getFirstName());
        assertEquals(studentRepository.findById(student.getId()).get().getFirstName(),
                userService.myDetails(student.getId()).getFirstName());
    }
}