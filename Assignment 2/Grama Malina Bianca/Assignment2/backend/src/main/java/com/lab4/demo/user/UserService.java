package com.lab4.demo.user;

import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lab4.demo.user.model.ERole.ADMIN;
import static com.lab4.demo.user.model.ERole.EMPLOYEE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(user -> {
                    UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
                    userMapper.populateRoles(user, userListDTO);
                    return userListDTO;
                }).collect(toList());
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    public ResponseEntity<?> create(UserListDTO user){
        if (authService.existsByUsername(user.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (authService.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        SignupRequest signupRequest = new SignupRequest(user.getName(), user.getEmail(), user.getPassword(), null);
        authService.register(signupRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserListDTO edit(Long id, UserListDTO user) {
        User userToUpdate = findById(id);

        userToUpdate.setUsername(user.getName());
        userToUpdate.setEmail(user.getEmail());
        if(!Objects.equals(user.getPassword(), userToUpdate.getPassword()) && user.getPassword() != null) {
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }

//        Set<String> roles = user.getRoles();
//        if(roles.contains("ADMIN") && !userToUpdate.getRoles().contains(roleRepository.findByName(ADMIN))) {
//            userToUpdate.setRoles(Set.of(roleRepository.findByName(ADMIN).orElse(null)));
//        } else if (roles.contains("EMPLOYEE") && !userToUpdate.getRoles().contains(roleRepository.findByName(EMPLOYEE))) {
//            userToUpdate.setRoles(Set.of(roleRepository.findByName(EMPLOYEE).orElse(null)));
//        }

        User savedUser = userRepository.save(userToUpdate);
        UserListDTO userListDTO = userMapper.userListDtoFromUser(savedUser);
        // userMapper.populateRoles(savedUser, userListDTO);
        return userListDTO;
    }

    public UserListDTO getUser(Long id) {
        return userMapper.userListDtoFromUser(findById(id));
    }
}
