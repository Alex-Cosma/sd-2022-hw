package com.forum.thread;

import com.forum.user.UserRepository;
import com.forum.user.model.User;
import com.forum.category.CategoryRepository;
import com.forum.category.model.Category;
import com.forum.post.PostRepository;
import com.forum.thread.mapper.TopicMapper;
import com.forum.thread.model.Topic;
import com.forum.thread.model.dto.TopicDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.forum.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class TopicServiceTest {
    @InjectMocks
    private TopicService topicService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private TopicMapper topicMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        topicService = new TopicService(topicRepository, topicMapper, userRepository, categoryRepository, postRepository);
    }

    @Test
    void findAll() {
        List<Topic> topics = listOf(Topic.class);
        when(topicRepository.findAll()).thenReturn(topics);

        List<TopicDTO> all = topicService.findAll();

        assertEquals(topics.size(), all.size());
    }

    @Test
    void findAllByCategoryId() {
        List<Topic> topics = listOf(Topic.class);
        when(topicRepository.findAllByCategoryId(1L)).thenReturn(topics);

        List<TopicDTO> all = topicService.findAllByCategoryId(1L);

        assertEquals(topics.size(), all.size());
    }

    @Test
    void testCreateTopic(){
        Category category = newCategory();
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        User user = newUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Topic topic = newTopic();
        topic.setCategory(category);
        topic.setUser(user);
        when(topicRepository.save(topic)).thenReturn(topic);

        given(topicMapper.toDto(topic)).willReturn(
                TopicDTO.builder()
                        .id(topic.getId())
                        .categoryName(topic.getCategory().getName())
                        .originalPoster(topic.getUser().getUsername())
                        .title(topic.getTitle())
                        .content(topic.getContent())
                        .creationDate(topic.getCreationDate())
                        .build()
        );

        given(topicMapper.fromDto(topicMapper.toDto(topic))).willReturn(topic);

        TopicDTO result = topicService.create(topicMapper.toDto(topic));

        assertEquals(topic.getId(), result.getId());
    }

    @Test
    void testEditTopic(){
        Category category = newCategory();
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        User user = newUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Topic topic = newTopic();
        when(topicRepository.findById(topic.getId())).thenReturn(java.util.Optional.of(topic));
        when(topicRepository.save(topic)).thenReturn(topic);

        Topic topic1 = newTopic();
        topic1.setCategory(category);
        topic1.setUser(user);

        given(topicMapper.toDto(topic1)).willReturn(
                TopicDTO.builder()
                        .id(topic1.getId())
                        .categoryName(topic1.getCategory().getName())
                        .originalPoster(topic1.getUser().getUsername())
                        .title(topic1.getTitle())
                        .content(topic1.getContent())
                        .creationDate(topic1.getCreationDate())
                        .build()
        );

        when(topicService.edit(topic.getId(), topicMapper.toDto(topic1))).thenReturn(
                TopicDTO.builder()
                        .id(topic1.getId())
                        .categoryName(topic1.getCategory().getName())
                        .originalPoster(topic1.getUser().getUsername())
                        .title(topic1.getTitle())
                        .content(topic1.getContent())
                        .creationDate(topic1.getCreationDate())
                        .build()
        );

        TopicDTO result = topicService.edit(topic.getId(), topicMapper.toDto(topic1));

        assertEquals(topic1.getId(), result.getId());
    }

    @Test
    void testDeleteTopic(){
        Topic topic = newTopic();
        when(topicRepository.findById(topic.getId())).thenReturn(java.util.Optional.of(topic));

        given(topicMapper.toDto(topic)).willReturn(
                TopicDTO.builder()
                        .id(topic.getId())
                        .categoryName(topic.getCategory().getName())
                        .originalPoster(topic.getUser().getUsername())
                        .title(topic.getTitle())
                        .content(topic.getContent())
                        .creationDate(topic.getCreationDate())
                        .build()
        );

        TopicDTO result = topicService.delete(topic.getId());

        assertEquals(topic.getId(), result.getId());
    }
}
