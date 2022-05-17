package com.lab4.demo.quizzSession;

import com.lab4.demo.quizz.QuizzService;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.quizzSession.model.QuizzSession;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.lab4.demo.user.model.ERole.ADMIN;
import static com.lab4.demo.user.model.ERole.CLIENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class QuizzSessionRepositoryTest {

    @Autowired
    private QuizzSessionRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuizzService quizzService;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,CLIENT));
    }

    @Test
    public void findAll() {
        User user = User.builder()
                .username("newUser")
                .password("password")
                .email("newUser@email.com")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .username("newUser")
                .password("password")
                .email("newUser@email.com")
                .build();
        userDTO = userService.create(userDTO);
        user.setId(userDTO.getId());

        Quizz quizz = Quizz.builder()
                .id(1L)
                .description("description")
                .points(1)
                .questions(null)
                .title("title")
                .build();

        QuizzDTO quizzDTO = QuizzDTO.builder()
                .id(1L)
                .description("description")
                .points(1)
                .questions(null)
                .title("title")
                .build();

        quizzDTO = quizzService.create(quizzDTO);
        quizz.setId(quizzDTO.getId());
        QuizzSession quizzSaved = repository.save(QuizzSession.builder()
                .id(1L)
                .quizz(quizz)
                .answerSequence(null)
                .user(user)
                .score(1)
                .build());
        List<QuizzSession> all = repository.findAll();
        assertEquals(all.size(), 1);
    }

    @Test
    public void findById() {
        User user = User.builder()
                .username("newUser2")
                .password("password")
                .email("newUser2@email.com")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .username("newUser2")
                .password("password")
                .email("newUser2@email.com")
                .build();
        userDTO = userService.create(userDTO);
        user.setId(userDTO.getId());

        Quizz quizz = Quizz.builder()
                 .id(1L)
                .description("description")
                .points(1)
                .questions(null)
                .title("title")
                .build();

        QuizzDTO quizzDTO = QuizzDTO.builder()
                .id(1L)
                .description("description")
                .points(1)
                .questions(null)
                .title("title")
                .build();
        quizzDTO = quizzService.create(quizzDTO);
        quizz.setId(quizzDTO.getId());
        QuizzSession quizzSaved = repository.save(QuizzSession.builder()
                .id(1L)
                .quizz(quizz)
                .answerSequence(null)
                .user(user)
                .score(1)
                .build());
        QuizzSession quizzFound = repository.findById(quizzSaved.getId()).get();

        assertEquals(quizzSaved.getId(), quizzFound.getId());
    }

    @Test
    public void create() {
        User user = User.builder()
                .username("username10")
                .password("password")
                .email("email10@email.com")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .username("username10")
                .password("password")
                .email("email10@email.com")
                .build();
        userDTO = userService.create(userDTO);
        user.setId(userDTO.getId());

        Quizz quizz = Quizz.builder()
                .id(1L)
                .description("description")
                .points(1)
                .questions(null)
                .title("title")
                .build();

        QuizzDTO quizzDTO = QuizzDTO.builder()
                .id(1L)
                .description("description")
                .points(1)
                .questions(null)
                .title("title")
                .build();

      quizzDTO = quizzService.create(quizzDTO);
      quizz.setId(quizzDTO.getId());
        QuizzSession quizzSaved = repository.save(QuizzSession.builder()
                .id(1L)
                .quizz(quizz)
                .answerSequence(null)
                .user(user)
                .score(1)
                .build());

        assertNotNull(quizzSaved);
        assertEquals(1,repository.findAll().size());
    }

}
