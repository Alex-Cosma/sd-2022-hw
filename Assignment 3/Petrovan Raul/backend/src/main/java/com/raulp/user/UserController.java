package com.raulp.user;

import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.dto.user.UserDetailsDTO;
import com.raulp.user.dto.user.UserListDTO;
import com.raulp.UrlMapping;
import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.services.InstructorService;
import com.raulp.user.services.StudentService;
import com.raulp.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.API_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final StudentService studentService;
    private final InstructorService instructorService;

    @GetMapping(UrlMapping.USER)
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @GetMapping(UrlMapping.USER_DETAILS)
    public UserDetailsDTO myDetails(@PathVariable Long id) {
        return userService.myDetails(id);
    }

    @GetMapping(UrlMapping.STUDENTS)
    public List<UserMinimalDTO> allStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(UrlMapping.INSTRUCTORS)
    public List<InstructorMinimalDTO> allInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping(UrlMapping.MY_STUDENTS)
    public List<StudentMinimalDTO> getStudentsForInstructor(@PathVariable Long id) {
        return instructorService.getStudentsForInstructor(id);
    }

    @GetMapping(UrlMapping.UNASSIGNED_STUDENTS)
    public List<StudentMinimalDTO> getUnassignedStudents() {
        return instructorService.getUnassignedStudents();
    }

    @PutMapping(UrlMapping.USER_DETAILS)
    public void updateUser(@PathVariable Long id, @RequestBody UserDetailsDTO user) {
        userService.updateUser(user, id);
    }

    @PutMapping(UrlMapping.ADD_STUDENT)
    public void addStudent(@PathVariable Long studentId, @PathVariable Long instructorId) {
        instructorService.addStudent(instructorId, studentId);
    }

}
