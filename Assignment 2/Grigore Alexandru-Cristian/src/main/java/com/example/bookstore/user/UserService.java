package com.example.bookstore.user;

import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.dto.UserMinimalDTO;
import com.example.bookstore.user.mapper.UserMapper;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import com.example.bookstore.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<UserListDTO> list = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
            userMapper.populateRoles(user,userListDTO);
            if(userListDTO.getRoles().contains("REGULAR")) {
                list.add(userListDTO);
            }
        }
        return list;
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public UserListDTO edit(Long id, UserListDTO user){
        User actUser = userRepository.getById(id);
        Set<String> roles = user.getRoles();
        Set<Role> rolesS = new HashSet<>();
        /*actUser.setRoles(user.getRoles().stream().map(s -> {
            final Role role1 = new Role();
            role1.setRole(ERole.valueOf(s));
            return role1;
        }).collect(Collectors.toSet()));*/
        roles.forEach(r->{
            Role ro = roleRepository.findByRole(ERole.valueOf(r))
                    .orElseThrow(()->new RuntimeException("no role"));
            rolesS.add(ro);
        });
        actUser.setRoles(rolesS);
        actUser.setUsername(user.getName());
        actUser.setEmail(user.getEmail());
        return userMapper.userListDtoFromUser(userRepository.save(actUser));
    }
}
