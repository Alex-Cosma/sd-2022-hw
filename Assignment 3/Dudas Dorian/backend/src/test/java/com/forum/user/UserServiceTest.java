package com.forum.user;

import com.forum.user.model.User;
import com.forum.TestCreationFactory;
import com.forum.post.PostRepository;
import com.forum.thread.TopicRepository;
import com.forum.user.dto.UserListDTO;
import com.forum.user.mapper.UserMapper;
import com.forum.user.model.ERole;
import com.forum.user.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.forum.TestCreationFactory.newUser;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
    private PostRepository postRepository;

    @Mock
    private TopicRepository topicRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, roleRepository, passwordEncoder, postRepository, topicRepository);
    }

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> all = userService.allUsersForList();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void testFindAllEmployees(){
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users){
            user.setRoles(Set.of(new Role(1, ERole.REGULAR)));
        }
        when(userRepository.findAll()).thenReturn(users);

        for(User user : users){
            given(userMapper.userListDtoFromUser(user)).willReturn(
                    UserListDTO.builder()
                            .name(user.getUsername())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .build()
            );

            UserListDTO userDTO = userMapper.userListDtoFromUser(user);
            userDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));

            doNothing().when(userMapper).populateRoles(user, userDTO);
        }

        List<User> filteredUsers = users.stream()
                .filter(
                        user -> user.getRoles().stream()
                                .anyMatch(role -> role.getName().equals(ERole.REGULAR))
                )
                .collect(toList());

        List<UserListDTO> result = userService.findAllRegularUsers();

        Assertions.assertEquals(filteredUsers.size(), result.size());
    }

    @Test
    void testCreateUser(){
        User user = newUser();
        when(userRepository.save(user)).thenReturn(user);

        given(userMapper.userListDtoFromUser(user)).willReturn(
                UserListDTO.builder()
                        .name(user.getUsername())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build()
        );

        UserListDTO userDTO = userMapper.userListDtoFromUser(user);
        userDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));

        doNothing().when(userMapper).populateRoles(user, userDTO);

        given(userMapper.userFromUserListDTO(userMapper.userListDtoFromUser(user))).willReturn(user);

        when(roleRepository.findByName(ERole.REGULAR)).thenReturn(Optional.of(new Role(1, ERole.REGULAR)));

        UserListDTO result = userService.create(userMapper.userListDtoFromUser(user));

        assertEquals(user.getUsername(), result.getName());
    }

    @Test
    void testEditUser(){
        User user = newUser();
        User user2 = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        given(userMapper.userListDtoFromUser(user)).willReturn(
                UserListDTO.builder()
                        .name(user.getUsername())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build()
        );

        UserListDTO userDTO = userMapper.userListDtoFromUser(user);
        userDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));

        doNothing().when(userMapper).populateRoles(user, userDTO);

        given(userMapper.userListDtoFromUser(user2)).willReturn(
                UserListDTO.builder()
                        .name(user2.getUsername())
                        .email(user2.getEmail())
                        .password(user2.getPassword())
                        .build()
        );

        userDTO = userMapper.userListDtoFromUser(user2);
        userDTO.setRoles(user2.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));

        doNothing().when(userMapper).populateRoles(user, userDTO);

        when(roleRepository.findByName(ERole.REGULAR)).thenReturn(Optional.of(new Role(1, ERole.REGULAR)));

        when(userService.edit(user.getId(), userMapper.userListDtoFromUser(user2))).thenReturn(
                UserListDTO.builder()
                        .name(user2.getUsername())
                        .email(user2.getEmail())
                        .password(user2.getPassword())
                        .build()
        );

        UserListDTO result = userService.edit(user.getId(), userMapper.userListDtoFromUser(user2));

        assertEquals(user2.getUsername(), result.getName());
    }

    @Test
    void testDeleteUser(){
        User user = newUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        given(userMapper.userListDtoFromUser(user)).willReturn(
                UserListDTO.builder()
                        .name(user.getUsername())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build()
        );

        UserListDTO userDTO = userMapper.userListDtoFromUser(user);
        userDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));

        doNothing().when(userMapper).populateRoles(user, userDTO);

        UserListDTO result = userService.delete(user.getId());

        assertEquals(user.getUsername(), result.getName());
    }
}
