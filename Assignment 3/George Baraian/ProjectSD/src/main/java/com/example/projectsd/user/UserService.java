package com.example.projectsd.user;

import com.example.projectsd.user.dto.UserListDTO;
import com.example.projectsd.user.dto.UserMinimalDTO;
import com.example.projectsd.user.mapper.UserMapper;
import com.example.projectsd.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        //todo: add email sending with reason of deletion from the system
    }

    public User findByUsername(String user) {

        return userRepository.findByUsername(user).get();
    }
}
