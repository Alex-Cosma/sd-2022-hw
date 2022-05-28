package com.raulp.user.services;

import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.mapper.UserMapper;
import com.raulp.user.repos.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    private final UserMapper userMapper;

    public List<UserMinimalDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(userMapper::userMinimalFromUser).collect(
                Collectors.toList());
    }
}
