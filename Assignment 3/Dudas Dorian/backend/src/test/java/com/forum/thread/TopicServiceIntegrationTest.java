package com.forum.thread;

import com.forum.post.PostRepository;
import com.forum.post.model.Post;
import com.forum.user.UserRepository;
import com.forum.user.model.User;
import com.forum.category.CategoryRepository;
import com.forum.category.model.Category;
import com.forum.thread.mapper.TopicMapper;
import com.forum.thread.model.Topic;
import com.forum.thread.model.dto.TopicDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.forum.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TopicServiceIntegrationTest {
    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setup() {
        topicRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        Category category = newCategory();
        categoryRepository.save(category);

        User user = newUser();
        userRepository.save(user);

        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(0));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);

        List<TopicDTO> topicsFound = topicService.findAll();

        assertEquals(topics.size(), topicsFound.size());
    }

    @Test
    public void testFindById() {
        Category category = newCategory();
        categoryRepository.save(category);

        User user = newUser();
        userRepository.save(user);

        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(0));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);
        List<TopicDTO> topicsSaved = topicService.findAll();

        Topic topicFound = topicService.findById(topicsSaved.get(0).getId());

        assertEquals(topics.get(0).getTitle(), topicFound.getTitle());
    }

    @Test
    public void testSave() {
        Category category = newCategory();
        categoryRepository.save(category);

        User user = newUser();
        userRepository.save(user);

        Topic topic = newTopic();
        topic.setCategory(categoryRepository.findAll().get(0));
        topic.setUser(userRepository.findAll().get(0));

        TopicDTO dto = topicMapper.toDto(topic);
        topicMapper.setCategoryAndOP(topic, dto);

        TopicDTO topicSaved = topicService.create(dto);

        assertEquals(topic.getTitle(), topicSaved.getTitle());
    }

    @Test
    public void testUpdate() {
        Category category = newCategory();
        categoryRepository.save(category);

        User user = newUser();
        userRepository.save(user);

        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(0));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);
        List<TopicDTO> topicsSaved = topicService.findAll();

        TopicDTO topic2 = newTopicDTO();
        topic2.setCategoryName(categoryRepository.findAll().get(0).getName());
        topic2.setOriginalPoster(userRepository.findAll().get(0).getUsername());

        TopicDTO topicUpdated = topicService.edit(topicsSaved.get(0).getId(), topic2);

        assertNotEquals(topics.get(0).getTitle(), topicUpdated.getTitle());
    }

    @Test
    public void testDelete() {
        Category category = newCategory();
        categoryRepository.save(category);

        User user = newUser();
        userRepository.save(user);

        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(0));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);
        List<TopicDTO> topicsSaved = topicService.findAll();

        topicService.delete(topicsSaved.get(0).getId());

        assertEquals(topics.size() - 1, topicService.findAll().size());

        // delete posts from topic also
        List<Post> posts = listOf(Post.class);
        for (Post post : posts) {
            post.setTopic(topicRepository.findAll().get(0));
            post.setUser(userRepository.findAll().get(0));
        }
        postRepository.saveAll(posts);

        topicService.delete(topicsSaved.get(1).getId());

        assertEquals(topics.size() - 2, topicService.findAll().size());
        assertEquals(0, postRepository.findAll().size());
    }
}
