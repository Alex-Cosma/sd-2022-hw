package com.example.backend.user;

import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.mapper.UserMapper;
import com.example.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    private User findUserById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

/*    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }*/

    public List<UserDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(toList());
    }

    public UserDTO edit(Long id, UserDTO item) {
        User actUser = findUserById(id);
        System.out.println(actUser.getEmail());
        actUser.setUsername(item.getUsername());
        actUser.setEmail(item.getEmail());
        actUser.setPassword(encoder.encode(item.getPassword()));
        actUser.setRoles(actUser.getRoles());
        return userMapper.toDto(
                userRepository.save(actUser));
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
