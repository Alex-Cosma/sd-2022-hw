package com.user;

import com.group.GroupMapper;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.security.AuthService;
import com.security.dto.MessageResponse;
import com.security.dto.SignupRequest;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;
import com.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final GroupMapper groupMapper;

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found" + id));
    }

    public ResponseEntity<MessageResponse> create(UserListDto user) {

        if (authService.existsByUsername(user.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (authService.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        SignupRequest signUpRequest = new SignupRequest(user.getUsername(), user.getEmail(),
                user.getPassword(), user.getFirstName(), user.getLastName(), user.getAddress());

        authService.register(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public List<UserListDto> allUsersForList() {
        return userRepository.findAll()
                .stream().map(user -> {
                    UserListDto userListDto = userMapper.userListDtoFromUser(user);
                    userMapper.populateFriends(user, userListDto);
                    return userListDto;
                })
                .collect(toList());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserListDto edit(Long id, UserListDto user) {
        User userToUpdate = findById(id);
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setGroups(user.getGroups());
        return userMapper.userListDtoFromUser(userRepository.save(userToUpdate));
    }

    public UserListDto get(Long id) {
        User user = findById(id);
        UserListDto userListDto = userMapper.userListDtoFromUser(user);
        userMapper.populateFriends(user, userListDto);
        return userListDto;
    }


    public UserListDto addFriend(Long id, Long friendId) {
        User user = findById(id);
        User friend = findById(friendId);

//        System.out.println("user: " + user.getUsername());
//        System.out.println("friend: " + friend.getUsername());

        Set<User> friends = user.getFriends();
        friends.add(friend);
        user.setFriends(friends);
//        System.out.println(user.getFriends());

        userRepository.save(user);
        userRepository.save(friend);
        return userMapper.userListDtoFromUser(user);
    }


    public GroupDto addToGroup(Long id, GroupDto groupDto) {
        User user = findById(id);
        Group group = groupMapper.fromDto(groupDto);
        Set<Group> userGroups = user.getGroups();
        Set<Group> groups = new HashSet<>();
        if (userGroups != null) {
            groups.addAll(userGroups);
        }
        groups.add(group);
        user.setGroups(groups);
        userRepository.save(user);
        return groupMapper.toDto(group);
    }

    public Set<User> getFriends(Long id) {
        return get(id).getFriends();
    }
}