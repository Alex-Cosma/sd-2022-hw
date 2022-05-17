package com.raulp.user;

import com.raulp.user.dto.UserListDTO;
import com.raulp.user.dto.UserMinimalDTO;
import com.raulp.user.mapper.UserMapper;
import com.raulp.user.repos.UserRepository;
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
                .stream().map(user -> {
                    UserListDTO userDTO =
                            userMapper.userListDtoFromUser(user);
                    userMapper.populateRoles(user, userDTO);
                    return userDTO;
                })
                .collect(toList());
    }

}
