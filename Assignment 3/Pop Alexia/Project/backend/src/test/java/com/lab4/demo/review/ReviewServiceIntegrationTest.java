package com.lab4.demo.review;

import com.lab4.demo.review.model.Review;
import com.lab4.demo.review.model.dto.ReviewDTO;
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
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,CLIENT));
    }

    @Test
    void findAll() {
        UserDTO userDTO = UserDTO.builder()
                .username("username182")
                .password("password")
                .email("email182@yahoo.com")
                .rankingPoints(0)
                .build();

        userDTO =  userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

        Review review = Review.builder()
                .user(user)
                .rating("GOOD")
                .text("text")
                .build();
        reviewRepository.save(review);
        List<ReviewDTO> all = reviewService.findAll();

        Assertions.assertEquals(all.size(),1);
    }

    @Test
    void findById(){
        UserDTO userDTO = UserDTO.builder()
                .username("username873")
                .password("password")
                .email("email873@yahoo.com")
                .rankingPoints(0)
                .build();
        userDTO =  userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

        Review review = Review.builder()
                .user(user)
                .rating("GOOD")
                .text("text")
                .build();
        review = reviewRepository.save(review);
        ReviewDTO reviewDTO = reviewService.findById(review.getId());
        Assertions.assertEquals(review.getId(), reviewDTO.getId());
    }

    @Test
    void filter(){
        UserDTO userDTO = UserDTO.builder()
                .username("username201")
                .password("password")
                .email("email201@yahoo.com")
                .rankingPoints(0)
                .build();
        userDTO =  userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

        Review review = Review.builder()
                .user(user)
                .rating("GOOD")
                .text("text")
                .build();
        reviewRepository.save(review);
        List<ReviewDTO> all = reviewService.findAll();

        Assertions.assertEquals(all.size(),1);
    }

    @Test
    void create(){
        UserDTO userDTO = UserDTO.builder()
                .username("username401")
                .password("password")
                .email("email401@yahoo.com")
                .rankingPoints(0)
                .build();
        userDTO = userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

        ReviewDTO review = ReviewDTO.builder()
                .reviewer(user.getEmail())
                .rating("GOOD")
                .text("text")
                .build();

        review = reviewService.create(review);
        Assertions.assertTrue(reviewRepository.findById(review.getId()).isPresent());
    }

    @Test
    void edit(){
        UserDTO userDTO = UserDTO.builder()
                .username("username501")
                .password("password")
                .email("email501@email.com")
                .rankingPoints(0)
                .build();
        userDTO = userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

        ReviewDTO review = ReviewDTO.builder()
                .reviewer(user.getEmail())
                .rating("GOOD")
                .text("text")
                .build();
        review = reviewService.create(review);

        review.setText("new text");
        review = reviewService.edit(review.getId(),review);

        Assertions.assertEquals(reviewRepository.findById(review.getId()).get().getText(), "new text");
    }

    @Test
    void delete(){
        UserDTO userDTO = UserDTO.builder()
                .username("username601")
                .password("password")
                .email("email601@email.com")
                .rankingPoints(0)
                .build();
        userDTO = userService.create(userDTO);
        User user = userService.findById(userDTO.getId());
        user.setId(userDTO.getId());

       ReviewDTO review = ReviewDTO.builder()
               .reviewer(user.getEmail())
               .rating("GOOD")
               .text("text")
               .build();
        review = reviewService.create(review);
        reviewService.delete(review.getId());
        Assertions.assertTrue(reviewRepository.findById(review.getId()).isEmpty());
    }
}
