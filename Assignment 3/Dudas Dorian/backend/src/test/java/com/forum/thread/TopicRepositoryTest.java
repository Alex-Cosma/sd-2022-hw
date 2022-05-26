package com.forum.thread;

import com.forum.user.UserRepository;
import com.forum.user.model.User;
import com.forum.category.CategoryRepository;
import com.forum.category.model.Category;
import com.forum.thread.model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static com.forum.TestCreationFactory.listOf;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TopicRepositoryTest {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        topicRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        categoryRepository.save(Category.builder().name("test").build());
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());

        Topic topicSaved = topicRepository.save(
                Topic.builder()
                        .category(categoryRepository.findAll().get(0))
                        .user(userRepository.findAll().get(0))
                        .title("test")
                        .creationDate(new Date())
                        .build()
        );

        assertNotNull(topicSaved);

        assertThrows(DataIntegrityViolationException.class, () -> topicRepository.save(Topic.builder().build()));
    }

    @Test
    public void testFindAll() {
        categoryRepository.save(Category.builder().name("test").build());
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());
        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(0));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);
        List<Topic> topicsFound = topicRepository.findAll();
        assertEquals(topics.size(), topicsFound.size());
    }

    @Test
    public void testFindByCategory() {
        List<Category> categories = listOf(Category.class);
        categoryRepository.saveAll(categories);
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());
        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(new Random().nextInt(categories.size())));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);

        // By category
        List<Topic> topicsFound = topicRepository.findAllByCategory(categoryRepository.findAll().get(0));
        assertEquals(
                (int) topicRepository.findAll().stream()
                        .filter(topic -> topic.getCategory().equals(categoryRepository.findAll().get(0)))
                        .count(),

                topicsFound.size()
        );

        // By category id
        List<Topic> topicsFound2 = topicRepository.findAllByCategoryId(categoryRepository.findAll().get(0).getId());
        assertEquals(
                (int) topicRepository.findAll().stream()
                        .filter(topic -> Objects.equals(topic.getCategory().getId(), categoryRepository.findAll().get(0).getId()))
                        .count(),

                topicsFound2.size()
        );
    }

    @Test
    public void testFindByTitle(){
        categoryRepository.save(Category.builder().name("test").build());
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());
        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(0));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);
        Optional<Topic> topicFound = topicRepository.findByTitle("test");
        assertTrue(
                topicFound.isPresent() && topicFound.get().getTitle().equals("test") && topics.contains(topicFound.get())
                || // XOR
                        topicFound.isEmpty() && topics.stream().noneMatch(topic -> topic.getTitle().equals("test"))
        );

        Optional<Topic> topicFound2 = topicRepository.findByTitle(topics.get(0).getTitle());
        assertTrue(topicFound2.isPresent());
        assertEquals(topics.get(0).getTitle(), topicFound2.get().getTitle());
    }

    @Test
    public void testDeleteById(){
        categoryRepository.save(Category.builder().name("test").build());
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());
        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(0));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);
        topicRepository.deleteById(topicRepository.findAll().get(0).getId());
        assertEquals(topics.size() - 1, topicRepository.count());
    }
}
