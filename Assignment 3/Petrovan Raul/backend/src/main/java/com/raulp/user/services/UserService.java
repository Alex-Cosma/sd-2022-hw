package com.raulp.user.services;

import com.raulp.book.model.Book;
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
import com.raulp.user.repos.StudentRepository;
import com.raulp.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final UserMapper userMapper;

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(user -> {
                    UserListDTO userDTO =
                            userMapper.userListDtoFromUser(user);
                    userMapper.populateRoles(user, userDTO);
                    return userDTO;
                })
                .collect(toList());
    }

    public UserDetailsDTO myDetails(Long id) {
        User user = findById(id);
        UserDetailsDTO userDTO = userMapper.userDetailsFromUser(user);
        if(userRepository.findById(id).isPresent() && userRepository.findById(id).get().getClass().equals(Student.class)) {
            Student student = (Student) user;
            userDTO.setFirstName(student.getFirstName());
            userDTO.setLastName(student.getLastName());
        }
        if(userRepository.findById(id).isPresent() && userRepository.findById(id).get().getClass().equals(Instructor.class)) {
            Instructor instructor = (Instructor) user;
            userDTO.setFirstName(instructor.getFirstName());
            userDTO.setLastName(instructor.getLastName());
        }
        return userDTO;
    }

    public void updateUser(UserDetailsDTO user, Long id) {
        User actUser = findById(id);
        if(actUser.getRoles().stream().map(Role::getName).toList().contains(ERole.STUDENT)) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found: " + id));
            student.setFirstName(user.getFirstName());
            student.setLastName(user.getLastName());
            studentRepository.save(student);
        } else if(actUser.getRoles().stream().map(Role::getName).toList().contains(ERole.FLIGHT_INSTRUCTOR)) {
            Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Instructor not found: " + id));
            instructor.setFirstName(user.getFirstName());
            instructor.setLastName(user.getLastName());
            instructorRepository.save(instructor);
        }
    }
}
