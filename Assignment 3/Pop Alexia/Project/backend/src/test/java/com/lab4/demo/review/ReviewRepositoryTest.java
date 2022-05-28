package com.lab4.demo.review;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.review.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Review> reviews = TestCreationFactory.listOf(Review.class);
        repository.saveAll(reviews);
        List<Review> all = repository.findAll();
        assertEquals(reviews.size(), all.size());
    }

    @Test
    public void filterReviews(){
        Review review1 = Review.builder()
                .text("aa")
                .rating("aa")
                .user(null)
                .build();

        Review review2 = Review.builder()
                .text("ww")
                .rating("ww")
                .user(null)
                .build();

        repository.save(review1);
        repository.save(review2);

       PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Review> all = repository.findAllByRatingLikeOrTextLike("%a%", "%a%",pageRequest);

        assertEquals(1, all.getNumberOfElements());
    }

    @Test
    public void findById() {
        Review review = repository.save(Review.builder()
                .text("aa")
                .rating("aa")
                .user(null)
                .build());
        Review reviewFound = repository.findById(review.getId()).get();

        assertEquals(review.getId(), reviewFound.getId());
    }

    @Test
    public void create() {
        Review reviewSaved = repository.save(Review.builder()
                .text("aa")
                .rating("aa")
                .user(null)
                .build());

        assertNotNull(reviewSaved);
        assertEquals(1,repository.findAll().size());
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(Review.builder().build());
        });
    }

    @Test
    public void edit(){
        Review review = repository.save(Review.builder()
                .text("aa")
                .rating("aa")
                .user(null)
                .build());
        review.setText("newtitle");
        review = repository.save(review);

        assertEquals(repository.findById(review.getId()).get().getText(), "newtitle");
    }

    @Test
    public void delete(){
        Review review = repository.save(Review.builder()
                .text("aa")
                .rating("aa")
                .user(null)
                .build());
        repository.delete(review);
        assertTrue(repository.findAll().isEmpty());
    }

}
