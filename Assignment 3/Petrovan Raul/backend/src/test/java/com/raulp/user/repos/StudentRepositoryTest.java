package com.raulp.user.repos;

import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void testSave() {
        Student student =
                Student.builder().username("raulp").password("hehe").email("raulp@mail.com").build();
        studentRepository.save(student);
        assertEquals(1, studentRepository.count());
    }

    @Test
    void findByEmail() {
        Student student =
                Student.builder().username("raulp").password("hehe").email("raulpetrovan@gmail" +
                        ".com").build();
        studentRepository.save(student);
        assertEquals(true, studentRepository.findByEmail("raulp").isEmpty());
        assertEquals(true, studentRepository.findByEmail("raulpetrovan@gmail.com").isPresent());
    }

    @Test
    void findAllByInstructorId() {
        Instructor instructor = Instructor.builder().username("raulInstructor").password("hehe").
                email("raulpetrovan@gmail.com").build();
        Student student = Student.builder().username("raulStudent").password("hehe").email(
                "raulpetrovan@gmail").build();
        student.setInstructor(instructor);
        studentRepository.save(student);
        assertEquals(1, studentRepository.findAllByInstructorId(instructor.getId()).size());
    }

    @Test
    void findAllByInstructorIsNull() {
        Instructor instructor = Instructor.builder().username("raulInstructor").password("hehe").
                email("raulp@gmail.com").build();
        Student student = Student.builder().username("raulStudent").password("hehe")
                .email("rauls@gmail.com").build();
        studentRepository.save(student);
        assertEquals(1, studentRepository.findAllByInstructorIsNull().size());
        student.setInstructor(instructor);
        studentRepository.save(student);
        assertEquals(0, studentRepository.findAllByInstructorIsNull().size());
    }
}