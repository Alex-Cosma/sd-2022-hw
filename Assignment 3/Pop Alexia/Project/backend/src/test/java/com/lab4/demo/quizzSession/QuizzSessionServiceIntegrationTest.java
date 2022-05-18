package com.lab4.demo.quizzSession;

import com.lab4.demo.quizz.QuizzService;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.quizzSession.model.QuizzSession;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.lab4.demo.user.model.ERole.ADMIN;
import static com.lab4.demo.user.model.ERole.CLIENT;

@SpringBootTest
public class QuizzSessionServiceIntegrationTest {

    @Autowired
    private QuizzSessionService quizzSessionService;

    @Autowired
    private QuizzSessionRepository quizzSessionRepository;

    @Autowired
    private QuizzService quizzService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        quizzSessionRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,CLIENT));
    }

    @Test
    void findAll() {
        UserDTO userDTO = UserDTO.builder()
                .username("username1")
                .password("password")
                .email("email1@yahoo.com")
                .rankingPoints(0)
                .build();
        QuizzDTO quizzDTO = QuizzDTO.builder()
                .id(1L)
                .title("title")
                .description("description")
                .build();

        userDTO =  userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

        quizzDTO =  quizzService.create(quizzDTO);
        Quizz quizz = quizzService.findById(quizzDTO.getId());
        quizz.setId(quizzDTO.getId());

        QuizzSession quizzSession = QuizzSession.builder()
                .score(1)
                .user(user)
                .quizz(quizz)
                .answerSequence(null)
                .build();
        quizzSessionRepository.save(quizzSession);
        List<QuizzSessionDTO> all = quizzSessionService.findAll();

        Assertions.assertEquals(all.size(),1);
    }

    @Test
    void findById(){
        UserDTO userDTO = UserDTO.builder()
                .username("username3")
                .password("password")
                .email("email3@yahoo.com")
                .rankingPoints(0)
                .build();
        QuizzDTO quizzDTO = QuizzDTO.builder()
                .id(1L)
                .title("title")
                .description("description")
                .build();
        userDTO =  userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

        quizzDTO =  quizzService.create(quizzDTO);
        Quizz quizz = quizzService.findById(quizzDTO.getId());
        quizz.setId(quizzDTO.getId());

        QuizzSession quizzSession = QuizzSession.builder()
                .score(1)
                .user(user)
                .quizz(quizz)
                .answerSequence(null)
                .build();

        quizzSession = quizzSessionRepository.save(quizzSession);
        QuizzSessionDTO quizzSessionDTO = quizzSessionService.findById(quizzSession.getId());
        Assertions.assertEquals(quizzSession.getId(), quizzSessionDTO.getId());
    }

    @Test
    void create(){
        UserDTO user = UserDTO.builder()
                .username("username2")
                .password("password")
                .email("email2@yahoo.com")
                .rankingPoints(0)
                .build();
        user = userService.create(user);

        QuizzDTO quizz = QuizzDTO.builder()
                .title("title")
                .description("description")
                .build();
        quizz = quizzService.create(quizz);

        QuizzSessionDTO quizzSession = QuizzSessionDTO.builder()
                .score(1)
                .userId(user.getId())
                .quizzId(quizz.getId())
                .build();

        quizzSession = quizzSessionService.create(quizzSession);
        Assertions.assertTrue(quizzSessionRepository.findById(quizzSession.getId()).isPresent());
    }

    @Test
    void export(){
        UserDTO userDTO = UserDTO.builder()
                .username("user2112")
                .password("password")
                .email("user2112@email.com")
                .rankingPoints(0)
                .build();
        userDTO = userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

        QuizzDTO quizzDTO = QuizzDTO.builder()
                .title("quizzer")
                .description("descriptione")
                .build();

        quizzDTO = quizzService.create(quizzDTO);
        Quizz quizz = quizzService.findById(quizzDTO.getId());
        quizz.setId(quizzDTO.getId());

        QuizzSession quizzSession = QuizzSession.builder()
                .score(1)
                .user(user)
                .quizz(quizz)
                .build();
        quizzSessionRepository.save(quizzSession);

        String filePath = quizzSessionService.export(ReportType.PDF, user.getId());
        Assertions.assertTrue(filePath.contains("report.pdf"));
    }

}
