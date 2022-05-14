package com;

import com.post.PostRepository;
import com.post.model.Post;
import com.security.AuthService;
import com.security.dto.SignupRequest;
import com.user.RoleRepository;
import com.user.UserRepository;
import com.user.model.ERole;
import com.user.model.Role;
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

    private final PostRepository postRepository;

    private final AuthService authService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("groza.dominic@gmail.com")
                    .username("dominic1")
                    .password("@Dominic1")
                    .firstName("Dominic")
                    .lastName("Groza")
                    .address("Bucuresti")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("groza.dominic@yahoo.de")
                    .username("dominic")
                    .password("@Dominic2")
                    .firstName("Benjamin")
                    .lastName("Groza")
                    .address("Hagelstadt")
                    .roles(Set.of("USER"))
                    .build());

            for(int i=0;i<10;i++){
                Random rand=new Random();
                Post post=Post.builder()
                        .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit-------- " + rand.nextInt())
                        .likes((long) rand.nextInt(100))
                        .disLikes((long) rand.nextInt(100))
                        .user(userRepository.findByUsername("dominic").isPresent()?userRepository.findByUsername("dominic").get():null)
                        .created_at(Date.from(new Date().toInstant()))
                        .build();
                postRepository.save(post);
            }
        }
    }
}