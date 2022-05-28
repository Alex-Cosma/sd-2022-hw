package com.example.airbnb.review;

import com.example.airbnb.accommodation.AccommodationRepository;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.review.model.dto.ReviewDTO;
import com.example.airbnb.security.AuthService;
import com.example.airbnb.security.dto.SignupRequest;
import com.example.airbnb.user.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        accommodationRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Test
    void create(){
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }

        authService.register(SignupRequest.builder()
                .email("alex1@email.com")
                .username("alex1")
                .password("WooHoo1!")
                .roles(Set.of("GUEST"))
                .build());

        User user = userRepository.findByUsername("alex1").orElse(null);

        final Accommodation accommodation = Accommodation.builder().
                name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms(2)
                .bedrooms(2).beds(2).price(100.1).user(user).build();

        Accommodation accommodation1 = accommodationRepository.save(accommodation);

        ReviewDTO reviewDTO = ReviewDTO.builder().accommodationId(accommodation1.getId()).content("Comment").userId(user.getId()).build();

        ReviewDTO reviewDTO1 = reviewService.create(reviewDTO);

        assertEquals(reviewDTO1.getContent(), reviewDTO.getContent());
    }

}