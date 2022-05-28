package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO edit(Long id, UserListDTO userListDTO) {
        User actUser = findById(id);
        actUser.setUsername(userListDTO.getUsername());
        actUser.setEmail(userListDTO.getEmail());
        return userMapper.userListDtoFromUser(
                userRepository.save(actUser)
        );
    }

    public UserListDTO get(Long id) {
        return userMapper.userListDtoFromUser(findById(id));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
