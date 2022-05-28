package com.example.backend.service;

import com.example.backend.dto.user.UserCreationDto;
import com.example.backend.dto.user.UserGetDto;
import com.example.backend.dto.user.UserUpdateDto;
import com.example.backend.entity.user.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserGetDto createUser(UserCreationDto userCreationDto) {
        User user = userMapper.userCreationDtoToUser(userCreationDto);
        userRepository.save(user);

        return userMapper.userToUserGetDto(user);
    }

    public UserUpdateDto updateUser(UserUpdateDto userUpdateDto, int userId) {
        userRepository.save(
                userRepository
                        .getById(userId)
                        .setUsername(userUpdateDto.username));
        return userUpdateDto;
    }

    public UserGetDto getUserById(int userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::userToUserGetDto)
                .orElse(null);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
