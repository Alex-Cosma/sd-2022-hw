package com.example.youtubeish.user;

import com.example.youtubeish.security.AuthService;
import com.example.youtubeish.security.dto.SignupRequest;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.dto.UserListDTO;
import com.example.youtubeish.user.dto.UserMinimalDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    public List<UserMinimalDTO> allUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());

    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map( user-> {
                    UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
                    userMapper.populateRoles(user, userListDTO);
                    return userListDTO;
                })
                .collect(toList());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User Not Found with username: " + username)
        );
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void create(SignupRequest newUser) {
        authService.register(newUser);
    }

    public UserDTO findById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found: " + id)));
    }

    public UserDTO edit(Long id, UserDTO user) {
        User actUser = userMapper.fromDto(findById(id));
        actUser.setEmail(user.getEmail());
        actUser.setUsername(user.getName());
        actUser.setPassword(encoder.encode(user.getPassword()));
        actUser.setRoles(user.getRoles());
        return userMapper.toDto(userRepository.save(actUser));
    }

}
