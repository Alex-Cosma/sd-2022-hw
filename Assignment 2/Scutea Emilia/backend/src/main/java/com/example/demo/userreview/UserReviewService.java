package com.example.demo.userreview;

import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.bookreview.BookReviewService;
import com.example.demo.bookreview.model.Rating;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.User;
import com.example.demo.userreview.model.UserReview;
import com.example.demo.userreview.model.dto.UserReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReviewService {

    private final UserService userService;
    private final UserReviewRepository userReviewRepository;
    private final UserReviewMapper userReviewMapper;


    public String getRating(String rating) {
        for (Rating rating1 : Rating.values()) {
            if (rating1.name().equalsIgnoreCase(rating)) {
                return rating1.name();
            }
        }
        return Rating.AVERAGE.name();
    }

    public List<UserReviewDTO> getReviewsForUser(Long userId) {
        return userReviewRepository.findAllByUserId(userId)
                .stream()
                .map(userReviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserReviewDTO convertReview(BookReviewDTO bookReviewDTO){
        return UserReviewDTO.builder()
                .text(bookReviewDTO.getText())
                .rating(bookReviewDTO.getRating())
                .build();
    }

    public UserReviewDTO addReview(Long user_id, UserReviewDTO userReviewDTO) {
        User user = userService.findById(user_id);

        userReviewDTO.setUser(user);
        userReviewDTO.setRating(getRating(userReviewDTO.getRating()));

        return userReviewMapper.toDto(userReviewRepository.save(userReviewMapper.fromDto(userReviewDTO)));
    }
}
