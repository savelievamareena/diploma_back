package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.model.Review;
import by.iba.diploma_101_back.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @PostMapping("/reviews")
    public Review createReview(@RequestBody Review Review) {
        return reviewRepository.save(Review);
    }

    @GetMapping("/reviews/{id}")
    public Review getReviewById(@PathVariable(value = "id") int ReviewId) {
        return reviewRepository.findById(ReviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", ReviewId));
    }

    @PutMapping("/reviews/{id}")
    public Review updateReview(@PathVariable(value = "id") int ReviewId, @RequestBody Review ReviewDetails) {

        Review Review = reviewRepository.findById(ReviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", ReviewId));

        Review.setReviewText(ReviewDetails.getReviewText());
        Review.setSender(ReviewDetails.getSender());

        return reviewRepository.save(Review);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable(value = "id") int ReviewId) {
        Review Review = reviewRepository.findById(ReviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", ReviewId));

        reviewRepository.delete(Review);

        return ResponseEntity.ok().build();
    }
}
