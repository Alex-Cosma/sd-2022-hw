package com.app.user;

import com.app.review.ReviewService;
import com.app.user.dto.UserDTO;
import com.app.user.mapper.UserMapper;
import com.app.security.AuthService;
import com.app.security.dto.SignupRequest;
import com.app.user.model.ERole;
import com.app.user.model.Role;
import com.app.user.model.User;
import com.app.watchlist.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final PasswordEncoder encoder;

    public List<UserDTO> allUsers(){
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(toList());
    }

    public UserDTO create(UserDTO userDTO) {
        SignupRequest signupRequest = SignupRequest.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(Set.of("REGULAR_USER"))
                .build();

        authService.register(signupRequest);

        return userDTO;
    }

    public UserDTO edit(Long id, UserDTO userDTO) {
        Optional<User> actUser = userRepository.findById(id);
        User user = actUser.get();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }
}
