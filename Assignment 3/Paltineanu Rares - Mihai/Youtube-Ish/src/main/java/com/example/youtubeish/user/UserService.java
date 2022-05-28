package com.example.youtubeish.user;

import com.example.youtubeish.security.AuthService;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<UserDTO> allUsersDto() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(toList());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User Not Found with username: " + username)
        );
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO create(UserDTO newUser) {
        return userMapper.toDto(authService.register(newUser));
    }

    public UserDTO findById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found: " + id)));
    }

    public UserDTO edit(Long id, UserDTO user) {
        User actUser = userMapper.fromDto(findById(id));
        actUser.setEmail(user.getEmail());
        actUser.setUsername(user.getUsername());
        return userMapper.toDto(userRepository.save(actUser));
    }
}
