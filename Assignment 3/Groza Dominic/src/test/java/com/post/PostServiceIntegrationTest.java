package com.post;

import com.TestCreationFactory;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.post.model.Post;
import com.post.model.dto.PostDto;
import com.user.UserRepository;
import com.user.UserService;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;
import com.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static com.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceIntegrationTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAll() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);
        List<Post> posts = TestCreationFactory.listOf(Post.class);
        posts.forEach(post -> post.setUser(user));
        posts.forEach(post -> post.setLikes(12L));
        posts.forEach(post -> post.setDisLikes(3L));

        postRepository.saveAll(posts);

        List<PostDto> allPosts = postService.findAll();

        Assertions.assertEquals(posts.size(), allPosts.size());
    }

    @Test
    void create() {
        User user=newUser();
        user.setId(1L);
        userRepository.save(user);


        PostDto postDto = newPostDto();


        postRepository.save(postMapper.fromDto(postDto));
        PostDto obtained = postService.create(user.getId(), postDto);
        assertEquals(postDto.getId(), obtained.getId());
    }

    @Test
    void edit() {
        UserListDto userListDto = newUserListDto();
        userRepository.save(userMapper.userFromUserListDto(userListDto));

        PostDto postDto = newPostDto();
        postDto.setBody("body");

        postRepository.save(postMapper.fromDto(postDto));
        assertEquals("body", postDto.getBody());
        postDto.setBody("NBODY");
        PostDto edited = postService.edit(postDto.getId(), postDto);
        assertEquals("NBODY", edited.getBody());
    }

    @Test
    void delete() {

        PostDto postDto = newPostDto();
        Post post = postRepository.save(postMapper.fromDto(postDto));

        postService.delete(post.getId());
        assertFalse(postRepository.existsById(post.getId()));
    }

    @Test
    void get() {
        PostDto postDto = newPostDto();
        Post post = postRepository.save(postMapper.fromDto(postDto));

        PostDto postDto1 = postService.get(post.getId());
        assertNotNull(postDto1);
    }

    @Test
    void findByUserId() {
    }

    @Test
    void findPostsOfFriends() {
    }
}