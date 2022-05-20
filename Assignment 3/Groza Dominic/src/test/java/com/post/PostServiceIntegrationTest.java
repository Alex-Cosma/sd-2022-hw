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

import java.util.Date;
import java.util.List;

import static com.TestCreationFactory.newPost;
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
        User user = TestCreationFactory.newUser();
        user=userRepository.save(user);

        Post post = newPost();
        post.setUser(user);
        post.setCreated_at(Date.from(new Date().toInstant()));
        post.setLikes(12L);
        post.setDisLikes(3L);

        postRepository.save(post);
        PostDto obtained = postService.create(postMapper.toDto(post));
        assertEquals(postMapper.toDto(post).getId(), obtained.getId());
    }

    @Test
    void edit() {
        User user = TestCreationFactory.newUser();
        user.setPosts(null);
        user=userRepository.save(user);

        PostDto postDto = PostDto.builder()
                .id(2L)
                .body("body")
                .created_at(Date.from(new Date().toInstant()))
                .disLikes(11L)
                .likes(13L)
                .user(user)
                .build();

        postRepository.save(postMapper.fromDto(postDto));
        PostDto created = postService.create(postDto);
        assertEquals("body", created.getBody());
        created.setBody("NBODY");
        PostDto edited = postService.edit(created.getId(), created);
        assertEquals("NBODY", edited.getBody());
    }

    @Test
    void delete() {
        User user = TestCreationFactory.newUser();
        user.setPosts(null);
        user=userRepository.save(user);

        PostDto postDto = PostDto.builder()
                .body("body")
                .created_at(Date.from(new Date().toInstant()))
                .disLikes(11L)
                .likes(13L)
                .user(user)
                .build();

       Post post= postRepository.save(postMapper.fromDto(postDto));
        System.out.println(postRepository.existsById(post.getId()));
        postService.delete(post.getId());
        assertFalse(postRepository.existsById(post.getId()));
    }

    @Test
    void get() {
        User user = TestCreationFactory.newUser();

        user.setPosts(null);
        user=userRepository.save(user);

        PostDto postDto = PostDto.builder()
                .body("bodyasdfa")
                .created_at(Date.from(new Date().toInstant()))
                .disLikes(11L)
                .likes(13L)
                .user(user)
                .build();

        Post post=postRepository.save(postMapper.fromDto(postDto));
        PostDto postDto1= postService.get(post.getId());
        assertNotNull(postDto1);
    }

    @Test
    void findByUserId() {
    }

    @Test
    void findPostsOfFriends() {
    }
}