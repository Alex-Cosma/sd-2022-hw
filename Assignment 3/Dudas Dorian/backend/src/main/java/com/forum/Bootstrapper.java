package com.forum;

import com.forum.user.RoleRepository;
import com.forum.user.UserRepository;
import com.forum.category.CategoryRepository;
import com.forum.category.model.Category;
import com.forum.post.PostRepository;
import com.forum.post.model.Post;
import com.forum.security.AuthService;
import com.forum.security.dto.SignupRequest;
import com.forum.thread.TopicRepository;
import com.forum.thread.model.Topic;
import com.forum.user.model.ERole;
import com.forum.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final CategoryRepository categoryRepository;

    private final TopicRepository topicRepository;

    private final PostRepository postRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            postRepository.deleteAll();
            topicRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            categoryRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("ted@email.com")
                    .username("ted")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("ted1@email.com")
                    .username("ted1")
                    .password("WooHoo1!")
                    .roles(Set.of("REGULAR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("ted2@email.com")
                    .username("ted2")
                    .password("WooHoo1!")
                    .roles(Set.of("REGULAR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("ted3@email.com")
                    .username("ted3")
                    .password("WooHoo1!")
                    .roles(Set.of("REGULAR"))
                    .build());
            for (int i = 0; i < 10; i++) {
                categoryRepository.save(
                        Category.builder()
                                .name("Category " + i)
                                .description("Description " + i)
                                .build()
                );
            }
            Random random = new Random();
            for (Category category : categoryRepository.findAll()) {
                for (int i = 0; i < 5; i++) {
                    topicRepository.save(
                            Topic.builder()
                                    .category(category)
                                    .user(userRepository.findAll().get(random.nextInt((int) userRepository.count())))
                                    .title("Topic " + category.getId() + "." + i)
                                    .content("ContentContentContentContentContentContentContentContentContentContentContent " + i)
                                    .creationDate(new Date(System.currentTimeMillis()))
                                    .build()
                    );
                }
            }
            for (Topic topic : topicRepository.findAll()) {
                for (int i = 0; i < 5; i++) {
                    postRepository.save(
                            Post.builder()
                                    .topic(topic)
                                    .user(userRepository.findAll().get(random.nextInt((int) userRepository.count())))
                                    .content("ContentContentContentContentContentContentContentContentContentContentContent " + i)
                                    .creationDate(new Date(System.currentTimeMillis()))
                                    .build()
                    );
                }
            }
        }
    }
}
