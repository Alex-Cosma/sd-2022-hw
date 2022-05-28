package com.raulp.user.services;

import com.raulp.TestCreationFactory;
import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.mapper.InstructorMapper;
import com.raulp.user.mapper.StudentMapper;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import com.raulp.user.model.User;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InstructorServiceIntegrationTest {

    @Autowired
    InstructorService instructorService;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    InstructorMapper instructorMapper;

    @Test
    void getAllInstructors() {
        int nrUsers = 10;
        List<Instructor> instructors = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            Instructor instructor = Instructor.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            instructors.add(instructor);
            instructorRepository.save(instructor);
        }

        List<InstructorMinimalDTO> userMinimalDTOS = instructorService.getAllInstructors();

        for (int i = 0; i < nrUsers; i++) {
            int finalI = i;
            assertEquals(instructors.get(i).getUsername(),
                    userMinimalDTOS.stream()
                            .filter(user -> user.getId() == instructors.get(finalI).getId()).collect(
                                    Collectors.toList()).get(0).getName());
        }
    }

    @Test
    void getStudentsForInstructor() {
        int nrUsers = 10;
        List<Student> students = new ArrayList<>();
        Instructor instructor = Instructor.builder()
                .username("User Instr")
                .password(UUID.randomUUID().toString())
                .email("userInstructor@gmail.com")
                .flights(new HashSet<>())
                .build();
        instructor = instructorRepository.save(instructor);
        for (int i = 0; i < nrUsers; i++) {
            Student student = Student.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .flights(new HashSet<>())
                    .build();
            students.add(student);
            studentRepository.save(student);
        }

        List<StudentMinimalDTO> studentMinimalDTOS = instructorService.getStudentsForInstructor(instructor.getId());

        for (int i = 0; i < nrUsers; i++) {
            if(students.get(i).getInstructor() != null) {
                int finalI = i;
                assertEquals(students.get(i).getUsername(),
                        studentMinimalDTOS.stream()
                                .filter(user -> user.getId() == students.get(finalI).getId()).collect(
                                        Collectors.toList()).get(0).getName());
            }
        }
    }
}