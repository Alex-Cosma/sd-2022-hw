package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> findAll() {
        return userRepository.findAllByRolesName(ERole.CLIENT).stream().map(userMapper::toDto).collect(toList());}

    public User findByEmail(String reviewer) {
        return userRepository.findByEmail(reviewer).orElseThrow(() -> new EntityNotFoundException("User not found: " + reviewer));
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO create(UserDTO userDTO) {
        User actUser = userMapper.fromDto(userDTO);
        actUser.setPassword(passwordEncoder.encode(actUser.getPassword()));
        Role r = roleRepository.findByName(ERole.CLIENT).orElseThrow(() -> new EntityNotFoundException("Role not found: " + ERole.CLIENT));
        actUser.setRoles(Set.of(r));
        actUser.setRankingPoints(0);
        return userMapper.toDto(userRepository.save(actUser));
    }

    public List<UserDTO> filterUsers(String filter) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return userRepository.findAllByUsernameLikeOrEmailLike("%"+filter+"%","%"+ filter+"%",pageRequest)
                .stream().map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO edit(Long id,UserDTO userDTO) {
        User actUser = userRepository.findById(id).get();
        actUser.setUsername(userDTO.getUsername());
        actUser.setEmail(userDTO.getEmail());
        if(userDTO.getPassword() != null) {
            actUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return userMapper.toDto(userRepository.save(actUser));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
