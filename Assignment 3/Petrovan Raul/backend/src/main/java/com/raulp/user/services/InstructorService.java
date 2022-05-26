package com.raulp.user.services;

import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.mapper.InstructorMapper;
import com.raulp.user.mapper.StudentMapper;
import com.raulp.user.mapper.UserMapper;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final InstructorMapper instructorMapper;

    public List<InstructorMinimalDTO> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(instructorMapper::instructorMinimalFromInstructor).collect(
                        Collectors.toList());
    }

    public List<StudentMinimalDTO> getStudentsForInstructor(Long instructorId) {
        return studentRepository.findAllByInstructorId(instructorId).stream()
                .map(studentMapper::studentMinimalFromStudent).collect(
                        Collectors.toList());
    }

    public List<StudentMinimalDTO> getUnassignedStudents() {
        return studentRepository.findAllByInstructorIsNull().stream()
                .map(studentMapper::studentMinimalFromStudent).collect(
                        Collectors.toList());
    }

    public void addStudent(Long instructorId, Long studentId) {
        studentRepository.findById(studentId).ifPresent(student -> {
            student.setInstructor(instructorRepository.findById(instructorId).get());
            studentRepository.save(student);
        });
        instructorRepository.findById(instructorId).ifPresent(instructor -> {
            if (instructor.getStudents() == null) {
                instructor.setStudents(Set.of(studentRepository.findById(studentId).get()));
            } else {
                instructor.getStudents().add(studentRepository.findById(studentId).get());
            }
            instructorRepository.save(instructor);
        });
    }

    public void unassignStudent(Long studentId) {
        studentRepository.findById(studentId).ifPresent(student -> {
            if(student.getInstructor() != null) {
                instructorRepository.findById(student.getInstructor().getId()).ifPresent(instructor -> {
                    System.out.println(instructor.toString());
                    instructor.getStudents().remove(student);
                    instructorRepository.save(instructor);
                });
                student.setInstructor(null);
                studentRepository.save(student);
            }
        });
    }
}
