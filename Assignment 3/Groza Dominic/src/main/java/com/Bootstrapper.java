package com;

import com.group.GroupRepository;
import com.group.model.Group;
import com.post.PostRepository;
import com.post.model.Post;
import com.security.AuthService;
import com.security.dto.SignupRequest;
import com.user.UserRepository;
import com.user.UserService;
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


    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final UserService userService;

    private final AuthService authService;

    private final GroupRepository groupRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();

            authService.register(SignupRequest.builder()
                    .email("groza.dominic@gmail.com")
                    .username("dominic1")
                    .password("@Dominic1")
                    .firstName("Dominic")
                    .lastName("Groza")
                    .address("Bucuresti")
                    .build());
            authService.register(SignupRequest.builder()
                    .email("groza.dominic@yahoo.de")
                    .username("dominic")
                    .password("@Dominic2")
                    .firstName("Benjamin")
                    .lastName("Groza")
                    .address("Hagelstadt")
                    .build());
            authService.register(SignupRequest.builder()
                    .email("test@gmail.com")
                    .username("testfakefriend")
                    .password("@Dominid123")
                    .firstName("Fake")
                    .lastName("Groza")
                    .address("Bucale")
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
            for(int i=0;i<10;i++){
                Random rand=new Random();
                Post post=Post.builder()
                        .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit-------- " + rand.nextInt())
                        .likes((long) rand.nextInt(100))
                        .disLikes((long) rand.nextInt(100))
                        .user(userRepository.findByUsername("dominic1").isPresent()?userRepository.findByUsername("dominic1").get():null)
                        .created_at(Date.from(new Date().toInstant()))
                        .build();
                postRepository.save(post);
            }
            Random rand=new Random();
            Post post=Post.builder()
                    .body("FAKE POST")
                    .likes((long) rand.nextInt(100))
                    .disLikes((long) rand.nextInt(100))
                    .user(userRepository.findByUsername("testfakefriend").isPresent()?userRepository.findByUsername("testfakefriend").get():null)
                    .created_at(Date.from(new Date().toInstant()))
                    .build();

            postRepository.save(post);


            Long id1=userRepository.findByUsername("dominic").get().getId();
            Long id2=userRepository.findByUsername("dominic1").get().getId();
            userService.addFriend(id1, id2);
            //test


            for(int i=0;i<4;i++){
                Group group=Group.builder()
                        .name("Group "+rand.nextInt())
                        .users(Set.of(userRepository.findByUsername("testfakefriend").get(),userRepository.findByUsername("dominic1").get()))
                        .build();
                groupRepository.save(group);
            }
        }
    }
}