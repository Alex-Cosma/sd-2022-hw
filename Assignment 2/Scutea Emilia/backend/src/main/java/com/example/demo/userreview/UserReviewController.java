package com.example.demo.userreview;

import com.example.demo.userreview.model.dto.UserReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(REVIEWS_USERS)
@RequiredArgsConstructor
public class UserReviewController {
    private final UserReviewService userReviewService;

    @GetMapping(REVIEW_FOR_USER)
    public List<UserReviewDTO> getReviewsForUser(@PathVariable Long id){
        return userReviewService.getReviewsForUser(id);
    }
}
