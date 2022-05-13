package com.lab4.demo.user;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.NewUserMapper;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final NewUserMapper newUserMapper;

    private final PasswordEncoder e;

    private final RoleRepository roleRepository;
    private final AuthService authenthicationServer;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {

        Optional<Role> defaultRole = roleRepository.findByName(ERole.CUSTOMER);
        return userRepository.findAllByRolesContaining(defaultRole)
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserDTO> showAllUsers(){
        return userRepository.findAll()
                .stream().map(newUserMapper::toDto)
                .collect(toList());
    }

    public UserDTO edit(Long id, UserDTO user) {
        User actUser = findById(id);
        actUser.setUsername(user.getUsername());
        actUser.setEmail(user.getEmail());
        //actUser.setPassword(e.encode(user.getPassword()));

        return newUserMapper.toDto(userRepository.save(actUser));
    }

    public Boolean newUser(UserDTO user)
    {
        if(userRepository.existsByEmail(user.getEmail())==false&&userRepository.existsByUsername(user.getUsername())==false)
        {
            SignupRequest signupRequest = SignupRequest.builder().username(user.getUsername())
                            .email(user.getEmail())
                                    .password(user.getPassword())
                                            .roles(null).build();

            authenthicationServer.register(signupRequest);
        }
        return true;
    }
    public void delete(Long id)
    {
        User u=findById(id);
        userRepository.delete(u);
    }



}
