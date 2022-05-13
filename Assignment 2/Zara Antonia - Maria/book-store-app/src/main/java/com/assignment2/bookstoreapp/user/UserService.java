package com.assignment2.bookstoreapp.user;
import com.assignment2.bookstoreapp.book.model.Book;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import com.assignment2.bookstoreapp.book.validator.BookValidator;
import com.assignment2.bookstoreapp.report.ReportType;
import com.assignment2.bookstoreapp.security.AuthService;
import com.assignment2.bookstoreapp.security.dto.SignupRequest;
import com.assignment2.bookstoreapp.user.dto.UserListDTO;
import com.assignment2.bookstoreapp.user.dto.UserMinimalDTO;
import com.assignment2.bookstoreapp.user.dto.UserRegisterDTO;
import com.assignment2.bookstoreapp.user.mapper.UserMapper;
import com.assignment2.bookstoreapp.user.model.ERole;
import com.assignment2.bookstoreapp.user.model.Role;
import com.assignment2.bookstoreapp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.assignment2.bookstoreapp.user.model.ERole.EMPLOYEE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .filter(u -> u.getRoles().contains("EMPLOYEE"))
                .collect(toList());
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO findDTOById(Long id){
        return userMapper.userListDtoFromUser(
                userRepository.findById(id).get()
        );
    }


    public List<UserListDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::userListDtoFromUser)
                .collect(Collectors.toList());
    }

    public void create(UserRegisterDTO user) {
        authService.createNewUserFromDTO(user);
    }

    public UserRegisterDTO edit(Long id, UserRegisterDTO user) {
        User actUser = findById(id);
        actUser.setUsername(user.getName());
        UserRegisterDTO urd =  userMapper.userRegisterDtoFromUser(
                userRepository.save(actUser));
        return urd;
    }
    
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}