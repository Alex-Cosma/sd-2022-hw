package com.example.backend.service;

import com.example.backend.dto.user.SignInDto;
import com.example.backend.dto.user.UserCreationDto;
import com.example.backend.dto.user.UserGetDto;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public LoginService(UserService userService, UserMapper userMapper, UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public boolean signIn(SignInDto signInDto) {
        return userRepository.existsByUsernameAndPassword(signInDto.username, signInDto.password);
    }

    public UserGetDto signUp(UserCreationDto userCreationDto) {
        return userService.createUser(userCreationDto);
    }
}
