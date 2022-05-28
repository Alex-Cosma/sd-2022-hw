package com.policeSection.user;

import com.policeSection.TestCreationFactory;
import com.policeSection.security.AuthService;
import com.policeSection.security.dto.SignupRequest;
import com.policeSection.user.dto.UserDTO;
import com.policeSection.user.dto.UserMinimalDTO;
import com.policeSection.user.mapper.NewUserMapper;
import com.policeSection.user.mapper.UserMapper;
import com.policeSection.user.model.ERole;
import com.policeSection.user.model.Role;
import com.policeSection.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.policeSection.user.model.ERole.EMPLOYEE;
import static com.policeSection.user.model.ERole.POLICEMAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private  AuthService authenthicationServer;

    @Mock
    private NewUserMapper newUserMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role(1,EMPLOYEE));
        roleRepository.save(new Role(2,POLICEMAN));
        userService = new UserService(userRepository,userMapper,newUserMapper,passwordEncoder,roleRepository,authenthicationServer);
    }

    @Test
    void findAll() {
        User user = User.builder()
               .id(1L)
               .username("random")
               .email("email@email.com")
               .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,EMPLOYEE)))
               .build();
        User user2 = User.builder()
                .id(2L)
                .username("random")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,EMPLOYEE)))
                .build();
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);

        List<UserMinimalDTO> all = userService.allUsersMinimal();

        assertEquals(users.size(), all.size());
    }

    @Test
    void create(){
        UserDTO user = UserDTO.builder()
                .id(1L)
                .username("random")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,POLICEMAN)))
                .build();


        when(userRepository.existsByEmail("email@email.com")).thenReturn(false);
        when(userRepository.existsByUsername("random")).thenReturn(false);
        SignupRequest signupRequest = SignupRequest.builder().username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(null).build();
        doNothing().when(authenthicationServer).register(signupRequest);
        Boolean t=userService.newUser(user);
        Assertions.assertEquals(t,true);
    }

    @Test
    void edit(){
        Long id = 50L;
        User user = User.builder()
                .id(id)
                .username("whatever")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,EMPLOYEE)))
                .build();
        UserDTO userDTO = UserDTO.builder()
                .id(id)
                .username("whatever")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(3,POLICEMAN)))
                .build();

        when(newUserMapper.toDto(user)).thenReturn(userDTO);
        when(newUserMapper.fromDto(userDTO)).thenReturn(user);
        when(roleRepository.findByName(POLICEMAN)).thenReturn(Optional.of(Role.builder().name(POLICEMAN).build()));
        when(userRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(user));
        when(newUserMapper.toDto(userRepository.save(newUserMapper.fromDto(userDTO)))).thenReturn(userDTO);
         userService.newUser(userDTO);

        userDTO.setUsername("newwhatever");
        UserDTO editedUser = userService.edit(id,userDTO);
        Assertions.assertEquals("newwhatever" ,editedUser.getUsername());

    }

    @Test
    void delete(){
        Long id = 1L;
        User user = User.builder()
                .id(id)
                .username("whatever")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .build();

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        userService.delete(id);
        assertFalse(userRepository.existsById(id));

    }
}
