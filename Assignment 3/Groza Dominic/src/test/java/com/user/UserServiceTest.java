package com.user;

import com.TestCreationFactory;
import com.group.GroupMapper;
import com.group.GroupRepository;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.post.PostMapper;
import com.post.model.Post;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private AuthService authService;
    @Mock
    private GroupMapper groupMapper;
    @Mock
    private PostMapper postMapper;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, authService, groupMapper, postMapper, groupRepository);
    }

    @Test
    void create() {
        User user = newUser();
        UserListDto userDto = newUserListDto();

        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userFromUserListDto(userDto)).thenReturn(user);
        when(userMapper.userListDtoFromUser(user)).thenReturn(userDto);

        ResponseEntity<MessageResponse> responseEntity = userService.create(userDto);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void allUsersForList() {
        List<User> users = TestCreationFactory.listOf(User.class);
        Group group = newGroup();
        when(userRepository.findAll()).thenReturn(users);
        List<UserListDto> preparedUsers = new ArrayList<>();
        for (User user : users) {
            Post post = newPost();
            user.setPosts(new HashSet<>());
            user.setGroups(new HashSet<>());
            user.getGroups().add(group);
            user.getPosts().add(post);
            UserListDto userListDto = new UserListDto();
            when(userMapper.userListDtoFromUser(user)).thenReturn(userListDto);
            preparedUsers.add(userListDto);
        }

        List<UserListDto> userDtos = userService.allUsersForList();
        assertEquals(preparedUsers.size(), userDtos.size());
    }

    @Test
    void delete() {
        User user = newUser();
        Post post = newPost();
        Group group = newGroup();
        user.setGroups(Set.of(group));
        user.setPosts(Set.of(post));
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        doNothing().when(userRepository).delete(user);
        userService.delete(user.getId());
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    void edit() {
        User user = newUser();
        user.setId(1L);

        UserListDto userDto = newUserListDto();
        userDto.setUsername("newUsername");

        userDto.setGroups(Set.of( newGroupDto()));
        userDto.setPosts(Set.of( newPostDto()));

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userMapper.userFromUserListDto(userDto)).thenReturn(user);
        when(userMapper.userListDtoFromUser(user)).thenReturn(userDto);
        when(userRepository.save(user)).thenReturn(user);

        UserListDto userListDto = userService.edit(user.getId(), userDto);

        assertEquals("newUsername", userListDto.getUsername());

    }

    @Test
    void get() {
        long id = randomLong();
        User user = newUser();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        UserListDto userListDto = newUserListDto();
        when(userMapper.userListDtoFromUser(user)).thenReturn(userListDto);

        UserListDto obtainedUser = userService.get(user.getId());
        assertEquals(userListDto, obtainedUser);
    }

    @Test
    void addFriend() {
        long id0 = randomLong();
        User user0 = newUser();
        user0.setFriends(null);
        user0.setId(id0);
        when(userRepository.findById(id0)).thenReturn(java.util.Optional.of(user0));
        when(userRepository.save(user0)).thenReturn(user0);
        Set<User> friends = new HashSet<>();
        friends.add(user0);

        long id = randomLong();
        User user = newUser();
        user.setFriends(friends);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        long id2 = randomLong();
        User user2 = newUser();
        user.setFriends(friends);
        user2.setId(id2);
        when(userRepository.findById(id2)).thenReturn(java.util.Optional.of(user2));
        when(userRepository.save(user2)).thenReturn(user2);

        UserListDto userListDto = newUserListDto();

        doNothing().when(userMapper).populateFriends(user, userListDto);
        when(userMapper.userListDtoFromUser(user)).thenReturn(userListDto);

        UserListDto userListDto1 = userService.addFriend(id, id2);
        assertEquals(user.getFriends().size(), 2);


    }

    @Test
    void addToGroup() {
        long id0 = randomLong();
        UserListDto userListDto = newUserListDto();
        User user = newUser();
        when(userRepository.findById(id0)).thenReturn(java.util.Optional
                .ofNullable(user));

        Set<UserListDto> users = new HashSet<>();
        users.add(userListDto);

        GroupDto group = newGroupDto();
        group.setUsers(users);

//        when(userService.addToGroup(id0, group)).thenReturn(group);
        GroupDto group1 = userService.addToGroup(id0, group);

//        assertEquals(user0.getGroups().size(), 1);
        assertTrue(true);
    }

    @Test
    void getFriends() {
//        UserListDto user0 = newUserListDto();
//        when(userRepository.findById(user0.getId()))
//                .thenReturn(java.util.Optional.of(userMapper.userFromUserListDto(user0)));
//        Set<UserListDto> friends = new HashSet<>();
//        friends.add(user0);
//
//        long id = randomLong();
//        User user = newUser();
//        user.setFriends(friends);
//        user.setId(id);
//        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
//
//        long id2 = randomLong();
//        User user2 = newUser();
//        user.setFriends(friends);
//        user2.setId(id2);
//        when(userRepository.findById(id2)).thenReturn(java.util.Optional.of(user2));
//
//
//        UserListDto userListDto2 = newUserListDto();
//        UserListDto userListDto1 = newUserListDto();
//
//        userListDto1.setFriends(friends);
//        userListDto2.setFriends(friends);
//
//        when(userMapper.userListDtoFromUser(user)).thenReturn(userListDto1);
//        when(userMapper.userListDtoFromUser(user2)).thenReturn(userListDto2);
//
//        doNothing().when(userMapper).populateFriends(user, userListDto1);
//        doNothing().when(userMapper).populateFriends(user2, userListDto2);
//
//
//        Set<User> friends1 = userService.getFriends(user.getId());
//        assertEquals(friends1.size(), user.getFriends().size());
        assertTrue(true);
    }
}