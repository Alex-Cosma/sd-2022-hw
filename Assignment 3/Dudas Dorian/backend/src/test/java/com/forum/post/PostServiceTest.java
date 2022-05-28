package com.forum.post;

import com.forum.category.model.Category;
import com.forum.post.mapper.PostMapper;
import com.forum.post.model.Post;
import com.forum.post.model.dto.PostDTO;
import com.forum.thread.TopicRepository;
import com.forum.thread.model.Topic;
import com.forum.user.UserRepository;
import com.forum.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.forum.TestCreationFactory.*;
import static com.forum.TestCreationFactory.newUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository, postMapper, topicRepository, userRepository);
    }

    @Test
    void findAll() {
        List<Post> posts = listOf(Post.class);
        when(postRepository.findAll()).thenReturn(posts);

        List<PostDTO> all = postService.findAll();

        assertEquals(posts.size(), all.size());
    }

    @Test
    void findByTopicId() {
        List<Post> posts = listOf(Post.class);
        when(postRepository.findAllByTopicId(1L)).thenReturn(posts);

        List<PostDTO> all = postService.findAllByTopicId(1L);

        assertEquals(posts.size(), all.size());
    }

    @Test
    void createPost() {
        Topic topic = newTopic();
        when(topicRepository.findByTitle(topic.getTitle())).thenReturn(Optional.of(topic));

        User user = newUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Post post = newPost();
        post.setTopic(topic);
        post.setUser(user);
        when(postRepository.save(post)).thenReturn(post);

        given(postMapper.toDto(post)).willReturn(
                PostDTO.builder()
                        .id(post.getId())
                        .topicTitle(post.getTopic().getTitle())
                        .posterUsername(post.getUser().getUsername())
                        .content(post.getContent())
                        .creationDate(post.getCreationDate())
                        .build()
        );

        given(postMapper.fromDto(postMapper.toDto(post))).willReturn(post);

        PostDTO postDTO = postService.create(postMapper.toDto(post));

        assertEquals(post.getId(), postDTO.getId());
    }

    @Test
    void editPost(){
        Topic topic = newTopic();
        when(topicRepository.findByTitle(topic.getTitle())).thenReturn(Optional.of(topic));

        User user = newUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Post post = newPost();
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postRepository.save(post)).thenReturn(post);

        Post post1 = newPost();
        post1.setTopic(topic);
        post1.setUser(user);

        given(postMapper.toDto(post1)).willReturn(
                PostDTO.builder()
                        .id(post1.getId())
                        .topicTitle(post1.getTopic().getTitle())
                        .posterUsername(post1.getUser().getUsername())
                        .content(post1.getContent())
                        .creationDate(post1.getCreationDate())
                        .build()
        );

        when(postService.edit(post.getId(), postMapper.toDto(post1))).thenReturn(
                PostDTO.builder()
                        .id(post1.getId())
                        .topicTitle(post1.getTopic().getTitle())
                        .posterUsername(post1.getUser().getUsername())
                        .content(post1.getContent())
                        .creationDate(post1.getCreationDate())
                        .build()
        );

        PostDTO postDTO = postService.edit(post.getId(), postMapper.toDto(post1));

        assertEquals(post1.getId(), postDTO.getId());
    }

    @Test
    void deletePost(){
        Post post = newPost();
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        given(postMapper.toDto(post)).willReturn(
                PostDTO.builder()
                        .id(post.getId())
                        .topicTitle(post.getTopic().getTitle())
                        .posterUsername(post.getUser().getUsername())
                        .content(post.getContent())
                        .creationDate(post.getCreationDate())
                        .build()
        );

        PostDTO result = postService.delete(post.getId());

        assertEquals(post.getId(), result.getId());
    }
}
