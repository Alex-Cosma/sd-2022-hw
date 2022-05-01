package com.example.bookstore.user;

import com.example.bookstore.user.model.Role;
import com.example.bookstore.user.model.User;
import com.example.bookstore.user.model.dto.UserDTO;
import com.example.bookstore.user.model.dto.UserListDTO;
import com.example.bookstore.user.model.dto.UserMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static com.example.bookstore.user.model.ERole.EMPLOYEE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public List<UserDTO> allUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(toList());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO findById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id)));
    }

    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.fromDto(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));

        Role r = roleRepository.findByName(EMPLOYEE)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + EMPLOYEE));

        user.setRoles(Set.of(r));

        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO edit(Long id, UserDTO user) {
        User userToEdit = userRepository.findById(id).get();
        userToEdit.setUsername(user.getUsername());
        userToEdit.setEmail(user.getEmail());
        if(user.getPassword() != null) {
            userToEdit.setPassword(encoder.encode(user.getPassword()));
        }

        return userMapper.toDto(userRepository.save(userToEdit));
    }
}
