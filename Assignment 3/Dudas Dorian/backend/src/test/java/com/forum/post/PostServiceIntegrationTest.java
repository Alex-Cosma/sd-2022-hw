package com.forum.post;

import com.forum.category.CategoryRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.forum.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostServiceIntegrationTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        topicRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        Category category = newCategory();
        categoryRepository.save(category);

        User user = newUser();
        userRepository.save(user);

        Topic topic = newTopic();
        topic.setCategory(categoryRepository.findAll().get(0));
        topic.setUser(userRepository.findAll().get(0));
        topicRepository.save(topic);

        List<Post> posts = listOf(Post.class);
        for (Post post : posts) {
            post.setTopic(topicRepository.findAll().get(0));
            post.setUser(userRepository.findAll().get(0));
        }
        postRepository.saveAll(posts);

        List<PostDTO> postsFound = postService.findAll();

        assertEquals(posts.size(), postsFound.size());
    }

    @Test
    void savePost() {
        Category category = newCategory();
        categoryRepository.save(category);

        User user = newUser();
        userRepository.save(user);

        Topic topic = newTopic();
        topic.setCategory(categoryRepository.findAll().get(0));
        topic.setUser(userRepository.findAll().get(0));
        topicRepository.save(topic);

        Post post = newPost();
        post.setTopic(topicRepository.findAll().get(0));
        post.setUser(userRepository.findAll().get(0));
        postRepository.save(post);

        PostDTO dto = postMapper.toDto(post);
        postMapper.setTopicAndUser(post, dto);

        PostDTO postSaved = postService.create(dto);

        assertEquals(post.getContent(), postSaved.getContent());
    }
}
