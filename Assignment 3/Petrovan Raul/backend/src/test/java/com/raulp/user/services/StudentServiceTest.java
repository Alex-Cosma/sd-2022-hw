package com.raulp.user.services;

import com.raulp.TestCreationFactory;
import com.raulp.user.mapper.UserMapper;
import com.raulp.user.model.Student;
import com.raulp.user.repos.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentRepository, userMapper);
    }

    @Test
    void getAllStudents() {
        List<Student> students = TestCreationFactory.listOf(Student.class);
        when(studentRepository.findAll()).thenReturn(students);
        assertEquals(students.size(), studentService.getAllStudents().size());
    }
}