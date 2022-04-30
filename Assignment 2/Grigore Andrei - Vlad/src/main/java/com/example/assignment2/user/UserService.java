package com.example.assignment2.user;

import com.example.assignment2.user.dto.UserListDTO;
import com.example.assignment2.user.dto.UserMinimalDTO;
import com.example.assignment2.user.mapper.UserMapper;
import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import com.example.assignment2.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public List<UserMinimalDTO> allUsersMinimal(){
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(Collectors.toList());
    }

    public List<UserListDTO> allUsersForList(){
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(Collectors.toList());
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("User not found: "+id));
    }

    public UserListDTO edit(Long id, UserListDTO user){
        User editUser = findById(id);
        editUser.setUsername(user.getName());
        Set<String> roles = user.getRoles();
        Set<Role> editRoles = new HashSet<>();
        roles.forEach(role->{
            Role r = roleRepository.findByRole(ERole.valueOf(role)).orElseThrow(()->new RuntimeException("role not found"));
            editRoles.add(r);
        });
        editUser.setRoles(editRoles);
        editUser.setEmail(user.getEmail());
        return userMapper.userListDtoFromUser(userRepository.save(editUser));
    }

    public void delete(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }

    public User create(User user){
        return userRepository.save(user);
    }

}
