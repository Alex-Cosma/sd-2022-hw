package com.raulp.user.services;

import com.raulp.TestCreationFactory;
import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.mapper.InstructorMapper;
import com.raulp.user.mapper.StudentMapper;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InstructorServiceTest {

    @InjectMocks
    InstructorService instructorService;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private InstructorMapper instructorMapper;

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instructorService = new InstructorService(instructorRepository,  studentRepository,
                studentMapper, instructorMapper);
    }

    @Test
    void getAllInstructors() {
        List<Instructor> instructors = TestCreationFactory.listOf(Instructor.class);
        when(instructorRepository.findAll()).thenReturn(instructors);

        List<InstructorMinimalDTO> all = instructorService.getAllInstructors();
        assertEquals(instructors.size(), all.size());
    }

    @Test
    void getStudentsForInstructor() {
        Instructor instructor = TestCreationFactory.newInstructor();
        List<Student> students = TestCreationFactory.listOf(Student.class);
        when(studentRepository.findAllByInstructorId(instructor.getId())).thenReturn(students.stream().filter(s -> s.getInstructor() != null && s.getInstructor().getId() == instructor.getId()).collect(Collectors.toList()));
        List<StudentMinimalDTO> studentsForInstructor = instructorService.getStudentsForInstructor(instructor.getId());
        assertEquals(studentRepository.findAllByInstructorId(instructor.getId()).size(),
                studentsForInstructor.size());
    }

    @Test
    void getUnassignedStudents() {
        List<Student> students = TestCreationFactory.listOf(Student.class);
        when(studentRepository.findAllByInstructorIsNull()).thenReturn(students.stream().filter(s -> s.getInstructor() == null).collect(Collectors.toList()));
        List<StudentMinimalDTO> unassigned = instructorService.getUnassignedStudents();
        assertEquals(studentRepository.findAllByInstructorIsNull().size(), unassigned.size());
    }

    @Test
    void addStudent() {
        Student student = TestCreationFactory.newStudent();
        Instructor instructor = TestCreationFactory.newInstructor();
        when(studentRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
        when(instructorRepository.findById(instructor.getId())).thenReturn(java.util.Optional.of(instructor));
        instructorService.addStudent(instructor.getId(), student.getId());
        assertEquals(student.getInstructor(), instructor);
    }

    @Test
    void unassignStudent() {
        Student student = TestCreationFactory.newStudent();
        Instructor instructor = TestCreationFactory.newInstructor();
        instructor.setStudents(Set.of(student));
        student.setInstructor(instructor);
        when(studentRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
        when(instructorRepository.findById(student.getInstructor().getId())).thenReturn(java.util.Optional.of(student.getInstructor()));
        instructorService.unassignStudent(student.getId());
        assertNull(student.getInstructor());
    }
}