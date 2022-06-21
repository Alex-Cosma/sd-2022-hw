package com.app.banking.service;

import com.app.banking.data.dto.mapper.UserMapper;
import com.app.banking.data.dto.model.UserDto;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.exception.ErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new RuntimeException(format("Card with id %s not found", id)));
    }

    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND));
    }

    public UserDto addUser(UserDto userDto) {
        return userMapper.userToUserDto(
                userRepository.save(
                        userMapper.userDtoToUser(userDto)));
    }

    public UserDto update(Integer id, UserDto userDto) {
        User userEntity = userMapper.userDtoToUser(userDto);
        userEntity.setId(id);

        return userMapper.userToUserDto(userRepository.save(userEntity));
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}

