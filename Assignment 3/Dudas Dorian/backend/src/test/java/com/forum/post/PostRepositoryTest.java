package com.forum.post;

import com.forum.category.CategoryRepository;
import com.forum.category.model.Category;
import com.forum.post.model.Post;
import com.forum.thread.TopicRepository;
import com.forum.thread.model.Topic;
import com.forum.user.UserRepository;
import com.forum.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.forum.TestCreationFactory.listOf;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
        topicRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        categoryRepository.save(Category.builder().name("test").build());
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());
        topicRepository.save(Topic.builder()
                        .category(categoryRepository.findAll().get(0))
                        .user(userRepository.findAll().get(0))
                        .title("test")
                        .creationDate(new Date())
                        .build());
        Post savedPost = postRepository.save(
                Post.builder()
                        .topic(topicRepository.findAll().get(0))
                        .user(userRepository.findAll().get(0))
                        .content("test")
                        .creationDate(new Date())
                        .build()
        );
        assertNotNull(savedPost);

        assertThrows(DataIntegrityViolationException.class, () -> postRepository.save(Post.builder().build()));
    }

    @Test
    public void testFindAll() {
        categoryRepository.save(Category.builder().name("test").build());
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());
        topicRepository.save(Topic.builder()
                .category(categoryRepository.findAll().get(0))
                .user(userRepository.findAll().get(0))
                .title("test")
                .creationDate(new Date())
                .build());
        List<Post> posts = listOf(Post.class);
        for (Post post : posts) {
            post.setTopic(topicRepository.findAll().get(0));
            post.setUser(userRepository.findAll().get(0));
        }
        postRepository.saveAll(posts);

        List<Post> postsFound = postRepository.findAll();

        assertEquals(posts.size(), postsFound.size());
    }

    @Test
    public void testFindByTopic() {
        categoryRepository.save(Category.builder().name("test").build());
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());
        List<Topic> topics = listOf(Topic.class);
        for (Topic topic : topics) {
            topic.setCategory(categoryRepository.findAll().get(0));
            topic.setUser(userRepository.findAll().get(0));
        }
        topicRepository.saveAll(topics);

        List<Post> posts = listOf(Post.class);
        for (Post post : posts) {
            post.setTopic(topicRepository.findAll().get(new Random().nextInt(topics.size())));
            post.setUser(userRepository.findAll().get(0));
        }
        postRepository.saveAll(posts);

        List<Post> postsFound = postRepository.findAllByTopicId(topics.get(0).getId());

        assertEquals(
                posts.stream().filter(post -> post.getTopic().getId().equals(topics.get(0).getId())).count(),
                postsFound.size()
        );
    }

    @Test
    void testFindByUserId() {
        categoryRepository.save(Category.builder().name("test").build());
        List<User> users = listOf(User.class);
        userRepository.saveAll(users);
        topicRepository.save(Topic.builder()
                .category(categoryRepository.findAll().get(0))
                .user(userRepository.findAll().get(0))
                .title("test")
                .creationDate(new Date())
                .build());
        List<Post> posts = listOf(Post.class);
        for (Post post : posts) {
            post.setTopic(topicRepository.findAll().get(0));
            post.setUser(userRepository.findAll().get(new Random().nextInt(users.size())));
        }
        postRepository.saveAll(posts);

        List<Post> postsFound = postRepository.findAllByUserId(users.get(0).getId());

        assertEquals(
                posts.stream().filter(post -> post.getUser().getId().equals(users.get(0).getId())).count(),
                postsFound.size()
        );
    }

    @Test
    void testDeleteById() {
        categoryRepository.save(Category.builder().name("test").build());
        userRepository.save(User.builder().username("test").email("a@b.c").password("test").build());
        topicRepository.save(Topic.builder()
                .category(categoryRepository.findAll().get(0))
                .user(userRepository.findAll().get(0))
                .title("test")
                .creationDate(new Date())
                .build());
        List<Post> posts = listOf(Post.class);
        for (Post post : posts) {
            post.setTopic(topicRepository.findAll().get(0));
            post.setUser(userRepository.findAll().get(0));
        }
        postRepository.saveAll(posts);

        postRepository.deleteById(postRepository.findAll().get(0).getId());

        assertEquals(posts.size() - 1, postRepository.findAll().size());
    }
}
