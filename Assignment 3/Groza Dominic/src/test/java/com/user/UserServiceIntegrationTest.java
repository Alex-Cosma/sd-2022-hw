package com.user;

import com.TestCreationFactory;
import com.group.GroupMapper;
import com.group.GroupRepository;
import com.group.GroupService;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.security.AuthService;
import com.security.dto.MessageResponse;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;
import com.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthService authService;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupRepository groupRepository;



    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

    }

    @Test
    void create() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);
        ResponseEntity<MessageResponse> responseEntity =
                userService.create(userMapper.userListDtoFromUser(user));
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    void allUsersForList() {
        List<User> users = TestCreationFactory.listOf(User.class);
        userRepository.saveAll(users);
        List<UserListDto> allUsers = userService.allUsersForList();
        assertEquals(users.size(), allUsers.size());
    }

    @Test
    void delete() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);
        userService.delete(user.getId());
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    void edit() {

        User user = TestCreationFactory.newUser();
        Group group=TestCreationFactory.newGroup();
        Group savedGroup=groupRepository.save(group);

        user.setGroups(Set.of(savedGroup));
        user = userRepository.save(user);

        UserListDto userListDto = userMapper.userListDtoFromUser(user);
        GroupDto groupDto=groupMapper.toDto(savedGroup);
        userListDto.setGroups(Set.of(groupDto));
        userListDto.setUsername("newUsername");
        userListDto.setEmail("new@email.com");

        userService.edit(user.getId(), userListDto);

        assertEquals(userListDto.getUsername(), userRepository.findById(user.getId()).get().getUsername());
        assertEquals(userListDto.getEmail(), userRepository.findById(user.getId()).get().getEmail());
    }

    @Test
    void get() {

        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);

        UserListDto userListDto = userService.get(user.getId());
        assertEquals(user.getUsername(), userListDto.getUsername());
    }

    @Test
    void addFriend() {
//        User user0 = TestCreationFactory.newUser();
//        user0.setRoles(Set.of(Objects.requireNonNull(roleRepository.findByName(ERole.USER).orElse(null))));
//        user0 = userRepository.save(user0);
//
//        User user = TestCreationFactory.newUser();
//        user.setRoles(Set.of(Objects.requireNonNull(roleRepository.findByName(ERole.USER).orElse(null))));
//        user.setFriends(Set.of(user0));
//        user = userRepository.save(user);
//
//        User user1 = TestCreationFactory.newUser();
//        user1.setFriends(Set.of(user0));
//        user1.setRoles(Set.of(Objects.requireNonNull(roleRepository.findByName(ERole.USER).orElse(null))));
//        user1 = userRepository.save(user1);
//
//
//        System.out.println(user);
//
//        UserListDto userListDto = userService.addFriend(user.getId(), user1.getId());
//
//        userMapper.populateFriends(user, userListDto);
//        Integer size = userListDto.getFriends().size();
////        assertTrue(userListDto.getFriends().contains(user1));
//        assertEquals(size,2);
    }

    @Test
    void addToGroup() {

    }

    @Test
    void getFriends() {
        User user0 = TestCreationFactory.newUser();
        user0 = userRepository.save(user0);

        User user = TestCreationFactory.newUser();
        user.setFriends(Set.of(user0));
        user = userRepository.save(user);

        Set<UserListDto> users = userService.getFriends(user.getId());
        assertEquals(users.size(), 1);
    }
}