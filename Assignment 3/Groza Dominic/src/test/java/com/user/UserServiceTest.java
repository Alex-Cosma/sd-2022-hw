package com.user;

import com.TestCreationFactory;
import com.group.GroupMapper;
import com.security.AuthService;
import com.security.dto.MessageResponse;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;
import com.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.TestCreationFactory.newUser;
import static com.TestCreationFactory.newUserListDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  UserMapper userMapper;
    @Mock
    private  AuthService authService;
    @Mock
    private  GroupMapper groupMapper;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, authService, groupMapper);
    }

    @Test
    void create() {
        User user=newUser();
        UserListDto userDto=newUserListDto();

        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userFromUserListDto(userDto)).thenReturn(user);
        when(userMapper.userListDtoFromUser(user)).thenReturn(userDto);

        ResponseEntity<MessageResponse> responseEntity=userService.create(userDto);
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    void allUsersForList() {
        List<User> users = TestCreationFactory.listOf(User.class);

        when(userRepository.findAll()).thenReturn(users);

        List<UserListDto> userDtos = userService.allUsersForList();

        assertEquals(users.size(), userDtos.size());
    }

    @Test
    void delete() {
        User user=newUser();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        doNothing().when(userRepository).delete(user);
        userService.delete(user.getId());
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    void edit() {
        User user=newUser();
        UserListDto userDto=newUserListDto();
        userDto.setUsername("newUsername");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userMapper.userFromUserListDto(userDto)).thenReturn(user);
        when(userMapper.userListDtoFromUser(user)).thenReturn(userDto);
        when(userRepository.save(user)).thenReturn(user);

        System.out.println(userDto.getId()+user.getId());
        UserListDto userListDto=userService.edit(userDto.getId(),userDto);

        assertEquals("newUsername",userListDto.getUsername());

    }

    @Test
    void get() {
    }

    @Test
    void addFriend() {
    }

    @Test
    void addToGroup() {
    }

    @Test
    void getFriends() {
    }
}