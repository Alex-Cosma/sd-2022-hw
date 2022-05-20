package com.post;

import com.TestCreationFactory;
import com.group.GroupService;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.post.model.Post;
import com.post.model.dto.PostDto;
import com.user.UserService;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;
import com.user.model.ERole;
import com.user.model.User;
import com.user.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, postMapper, userMapper, userService);
    }

    @Test
    void findAll() {
        List<Post> posts = listOf(Post.class);
        when(postRepository.findAll()).thenReturn(posts);
        List<PostDto> preparedPostDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = new PostDto();
            when(postMapper.toDto(post)).thenReturn(postDto);
            preparedPostDtos.add(postDto);
        }

        List<PostDto> obtainedPostDtos = postService.findAll();
        assertEquals(posts.size(), obtainedPostDtos.size());
        for (int i = 0; i < obtainedPostDtos.size(); i++) {
            PostDto obtained = obtainedPostDtos.get(i);
            PostDto prepared = preparedPostDtos.get(i);
            assertEquals(obtained, prepared);
        }
        assertEquals(posts.size(), obtainedPostDtos.size());
        for (int i = 0; i < obtainedPostDtos.size(); i++) {
            PostDto obtained = obtainedPostDtos.get(i);
            PostDto prepared = preparedPostDtos.get(i);
            assertEquals(obtained, prepared);
        }
    }

    @Test
    void create() {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .body("body")
                .created_at(Date.from(new Date().toInstant()))
                .disLikes(11L)
                .likes(13L)
                .build();
        when(postMapper.toDto(postRepository.save(postMapper.fromDto(postDto)))).thenReturn(postDto);
        PostDto obtained = postService.create(postDto);
        assertEquals(postDto, obtained);

    }

    @Test
    void edit() {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .body("body")
                .created_at(Date.from(new Date().toInstant()))
                .disLikes(11L)
                .likes(13L)
                .build();
        Post post = Post.builder()
                .id(1L)
                .body("body")
                .created_at(Date.from(new Date().toInstant()))
                .disLikes(11L)
                .likes(13L)
                .build();
        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));
        when(postMapper.toDto(postRepository.save(postMapper.fromDto(postDto)))).thenReturn(postDto);
        PostDto created = postService.create(postDto);
        assertEquals("body", created.getBody());
        created.setBody("NBODY");
        PostDto edited = postService.edit(created.getId(), created);
        assertEquals("NBODY", edited.getBody());
    }

    @Test
    void delete() {
        Post post = Post.builder()
                .id(1L)
                .body("body")
                .created_at(Date.from(new Date().toInstant()))
                .disLikes(11L)
                .likes(13L)
                .build();
        when(postRepository.save(post)).thenReturn(post);
        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));
        when(postRepository.existsById(1L)).thenReturn(false);

        postService.delete(1L);
        assertFalse(postRepository.existsById(1L));
    }

    @Test
    void get() {
        Post post = new Post();
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
        PostDto postDto = newPostDto();
        when(postMapper.toDto(post)).thenReturn(postDto);

        PostDto obtainedPostDto = postService.get(1L);
        assertEquals(postDto, obtainedPostDto);
    }

    @Test
    void findByUserId() {
        UserListDto user = UserListDto.builder()
                .id(1L)
                .email("groza.dominic@gmail.com")
                .username("dominic1")
                .password("@Dominic1")
                .firstName("Dominic")
                .lastName("Groza")
                .address("Bucuresti")
                .build();
        userService.create(user);

        PostDto postDto = newPostDto();
        postDto.setUser(user);

        Post post= new Post();
        when(postMapper.fromDto(postDto)).thenReturn(post);
        user.setPosts(Collections.singleton(post));
        postService.create(postDto);

        System.out.println(postService.findAll().size()+"asdada "+user.getPosts().size());

        List<PostDto> obtained = postService.findByUserId(1L);
        assertEquals(1, obtained.size());
//        assertEquals(post,obtained.get(0));
    }

    @Test
    void findPostsOfFriends() {
    }
}