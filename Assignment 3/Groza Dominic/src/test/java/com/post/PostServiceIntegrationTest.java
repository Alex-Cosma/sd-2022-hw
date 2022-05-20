package com.post;

import com.TestCreationFactory;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.post.model.Post;
import com.post.model.dto.PostDto;
import com.user.UserRepository;
import com.user.UserService;
import com.user.mapper.UserMapper;
import com.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostServiceIntegrationTest {

    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private  PostMapper postMapper;
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private  UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    void findAll() {
        User user=TestCreationFactory.newUser();
        userRepository.save(user);
        List<Post> posts = TestCreationFactory.listOf(Post.class);
        posts.forEach(post -> post.setUser(user));
        postRepository.saveAll(posts);

        List <PostDto> allPosts= postService.findAll();

        Assertions.assertEquals(posts.size(), allPosts.size());
    }

    @Test
    void create() {
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }

    @Test
    void get() {
    }

    @Test
    void findByUserId() {
    }

    @Test
    void findPostsOfFriends() {
    }
}