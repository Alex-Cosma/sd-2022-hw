package com.raulp.user.services;

import com.raulp.TestCreationFactory;
import com.raulp.user.dto.user.UserDetailsDTO;
import com.raulp.user.mapper.UserMapper;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import com.raulp.user.model.User;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.StudentRepository;
import com.raulp.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, instructorRepository, studentRepository, userMapper);
    }

    @Test
    void allUsersMinimal() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(users.size(), userService.allUsersMinimal().size());
    }

    @Test
    void allUsersForList() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(users.size(), userService.allUsersForList().size());
    }

    @Test
    void myDetails() {
        User user = TestCreationFactory.newUser();
        Student student = TestCreationFactory.newStudent();
        Instructor instructor = TestCreationFactory.newInstructor();
        when(userMapper.userDetailsFromUser(user)).thenReturn(UserDetailsDTO.builder().name(user.getUsername()).id(user.getId()).build());
        when(userMapper.userDetailsFromUser(student)).thenReturn(UserDetailsDTO.builder().name(student.getUsername()).id(student.getId()).build());
        when(userMapper.userDetailsFromUser(instructor)).thenReturn(UserDetailsDTO.builder().name(instructor.getUsername()).id(instructor.getId()).build());

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        when(studentRepository.findById(user.getId())).thenReturn(java.util.Optional.of(student));
        when(instructorRepository.findById(user.getId())).thenReturn(java.util.Optional.of(instructor));
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
        when(userRepository.findById(instructor.getId())).thenReturn(java.util.Optional.of(instructor));
        assertEquals(user.getId(), userService.myDetails(user.getId()).getId());
        assertEquals(student.getFirstName(), userService.myDetails(student.getId()).getFirstName());
        assertEquals(instructor.getLastName(), userService.myDetails(instructor.getId()).getLastName());
    }

    @Test
    void updateUser() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(users.size(), userService.allUsersForList().size());
    }
}