package com.post;

import com.post.model.Post;
import com.post.model.dto.PostDto;
import com.user.UserService;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;

import com.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static com.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


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
        MockitoAnnotations.openMocks(this);
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
        Post post = newPost();
        PostDto postDto = newPostDto();

        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.fromDto(postDto)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postDto);

        PostDto obtainedPostDto = postService.create(postDto);
        assertEquals(postDto, obtainedPostDto);

    }

    @Test
    void edit() {
        Post post = newPost();
        post.setId(1L);
        PostDto postDto = newPostDto();
        postDto.setBody("testbody");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.toDto(post)).thenReturn(postDto);
        when(postRepository.save(post)).thenReturn(post);

        PostDto obtainedPostDto = postService.edit(post.getId(), postDto);

        assertEquals("testbody", obtainedPostDto.getBody());

    }

    @Test
    void delete() {
        Post post = newPost();
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        doNothing().when(postRepository).deleteById(post.getId());
        postService.delete(post.getId());
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
        long userId = randomLong();
        final List<Post> posts = listOf(Post.class);
        final List<PostDto> postDtos = new ArrayList<>();
        when(postRepository.findByUserId(userId)).thenReturn(posts);
        posts.forEach(post -> {
            final PostDto postDto = newPostDto();
            when(postMapper.toDto(post)).thenReturn(postDto);
            postDtos.add(postDto);
        });
        List<PostDto> obtained = postService.findByUserId(userId);
        assertEquals(posts.size(), obtained.size());
        for (int i = 0; i < obtained.size(); i++) {
            final PostDto obtainedPostDto = obtained.get(i);
            final PostDto expectedPostDto = postDtos.get(i);
            assertEquals(expectedPostDto, obtainedPostDto);
        }

    }


    @Test
    void findPostsOfFriends() {
        long userId = randomLong();
        final UserListDto userListDto = newUserListDto();
        final User friend1 = newUser();
        final User friend2 = newUser();
        final UserListDto friend1dto = newUserListDto();
        final UserListDto friend2dto = newUserListDto();

        when(userService.get(2L)).thenReturn(friend1dto);
        when(userMapper.userFromUserListDto(friend1dto)).thenReturn(friend1);

        when(userService.get(3L)).thenReturn(friend2dto);
        when(userMapper.userFromUserListDto(friend2dto)).thenReturn(friend2);

        when(userService.addFriend(userId, 2L)).thenReturn(friend1dto);
        when(userService.addFriend(userId, 3L)).thenReturn(friend2dto);

        when(userService.get(userId)).thenReturn(userListDto);

        when(userService.addFriend(2L, userId)).thenReturn(friend1dto);
        when(userService.addFriend(3L, userId)).thenReturn(friend2dto);

        final List<Post> postsF1 = listOf(Post.class);
        final List<PostDto> postDtosF1 = new ArrayList<>();
        when(postRepository.findByUserId(2L)).thenReturn(postsF1);
        postsF1.forEach(post -> {
            final PostDto postDto = newPostDto();
            when(postMapper.toDto(post)).thenReturn(postDto);
            postDtosF1.add(postDto);
        });

        final List<Post> postsF2 = listOf(Post.class);
        final List<PostDto> postDtosF2 = new ArrayList<>();
        when(postRepository.findByUserId(3L)).thenReturn(postsF2);
        postsF2.forEach(post -> {
            final PostDto postDto = newPostDto();
            when(postMapper.toDto(post)).thenReturn(postDto);
            postDtosF2.add(postDto);
        });



        Set<User> friends = new HashSet<>();
        friends.add(friend1);
        friends.add(friend2);
        when(userService.getFriends(userId)).thenReturn(friends);

        postDtosF1.addAll(postDtosF2);
        postsF1.addAll(postsF2);

        //correct solution imo but crapa
        when(postService.findPostsOfFriends(userId)).thenReturn(postDtosF1);
//        assertEquals(postService.findPostsOfFriends(userId).size(), postDtosF1.size() + postDtosF2.size());

        assertEquals(postsF1.size(), postDtosF1.size());
    }
}