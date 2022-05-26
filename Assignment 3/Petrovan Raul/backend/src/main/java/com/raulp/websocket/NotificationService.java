package com.raulp.websocket;

import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MessageController messageController;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public void sendNotification(Long studentId, Long instructorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        messageController.sendNotification(MessageDTO.builder()
                .message("A new flight was added by instructor " + instructor.getFirstName() + " " +
                        instructor.getLastName() + " (" + instructor.getUsername() + ") " +
                        " for student " + student.getFirstName() + " " + student.getLastName() +
                        " (" + student.getUsername() +
                        "). Check the 'My Flights' tab to see the flight " + "details.")
                .userIds(List.of(studentId.toString(), instructorId.toString()))
                .build());
    }
}
