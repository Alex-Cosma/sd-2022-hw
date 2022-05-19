package com.lab4.demo.review;

import com.lab4.demo.review.model.Review;
import com.lab4.demo.review.model.dto.ReviewDTO;
import com.lab4.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;


    public List<ReviewDTO> findAll() {
        return reviewRepository.findAll().stream().map(reviewMapper::toDto).collect(java.util.stream.Collectors.toList());
    }

    public List<ReviewDTO> filterReviews(String filter){
        PageRequest pageRequest = PageRequest.of(0, 10);
        return reviewRepository.findAllByRatingLikeOrTextLike("%"+filter+"%","%"+filter+"%",pageRequest).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReviewDTO findById(Long id) {
        return reviewMapper.toDto(reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review not found: " + id)));
    }

    public ReviewDTO create(ReviewDTO reviewDTO) throws ConstraintViolationException {
        Review entity = reviewMapper.fromDto(reviewDTO);
        entity.setUser(userService.findByEmail(reviewDTO.getReviewer()));
        return reviewMapper.toDto(reviewRepository.save(entity));
    }

    public ReviewDTO edit(Long id, ReviewDTO reviewDTO) throws ConstraintViolationException {
        Review actReview = reviewRepository.findById(id).get();
        actReview.setRating(reviewDTO.getRating());
        actReview.setText(reviewDTO.getText());
        return reviewMapper.toDto(reviewRepository.save(actReview));
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
