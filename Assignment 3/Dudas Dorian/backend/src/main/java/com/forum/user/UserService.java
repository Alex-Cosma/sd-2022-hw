package com.forum.user;

import com.forum.post.PostRepository;
import com.forum.post.model.Post;
import com.forum.thread.model.Topic;
import com.forum.user.dto.UserListDTO;
import com.forum.user.dto.UserMinimalDTO;
import com.forum.user.mapper.UserMapper;
import com.forum.user.model.ERole;
import com.forum.user.model.User;
import com.forum.thread.TopicRepository;
import com.forum.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(user -> {
                            UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
                            userMapper.populateRoles(user, userListDTO);
                            return userListDTO;
                        }
                )
                .collect(toList());
    }

    public UserListDTO create(UserListDTO userListDTO) {
        User user = userMapper.userFromUserListDTO(userListDTO);
        user.setPassword(encoder.encode(userListDTO.getPassword()));
        setupRoles(userListDTO, user);
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public UserListDTO edit(Long id, UserListDTO userListDTO) {
        User user = findById(id);
        user.setUsername(userListDTO.getName());
        user.setEmail(userListDTO.getEmail());
        user.setPassword(encoder.encode(userListDTO.getPassword()));
        setupRoles(userListDTO, user);
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    private void setupRoles(UserListDTO userListDTO, User user) {
        userListDTO.setRoles(Set.of("REGULAR"));
        Set<Role> roles = new HashSet<>();
        if (userListDTO.getRoles() == null) {
            Role defaultRole = roleRepository.findByName(ERole.REGULAR)
                    .orElseThrow(() -> new RuntimeException("Cannot find REGULAR role"));
            roles.add(defaultRole);
        } else {
            userListDTO.getRoles().forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }
        user.setRoles(roles);
    }


    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO delete(Long id) {
        List<Topic> topics = topicRepository.findByUserId(id);
        List<Long> topicIds = topics.stream().map(Topic::getId).collect(Collectors.toList());
        for(Long topicId : topicIds){
            postRepository.deleteAll(postRepository.findAllByTopicId(topicId));
        }
        topicRepository.deleteAll(topics);

        List<Post> posts = postRepository.findAllByUserId(id);
        postRepository.deleteAll(posts);

        User user = findById(id);
        userRepository.delete(user);
        return userMapper.userListDtoFromUser(user);
    }

    public List<UserListDTO> findAllRegularUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.REGULAR)))
                .map(user -> {
                            UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
                            userMapper.populateRoles(user, userListDTO);
                            return userListDTO;
                        }
                )
                .collect(toList());
    }
}
