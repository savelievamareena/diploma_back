package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.forms.ReviewForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.Doctor;
import by.iba.diploma_101_back.model.Review;
import by.iba.diploma_101_back.repository.DoctorRepository;
import by.iba.diploma_101_back.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, DoctorRepository doctorRepository) {
        this.reviewRepository = reviewRepository;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/admin/reviews")
    public List<Review> getReviewsToModerate() {
        return reviewRepository.findByIsShown(false);
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> createReview(@RequestBody ReviewForm reviewForm) {
        ApiResponse apiResponse = new ApiResponse();

        Review review = new Review();

        review.setSender(reviewForm.getSender());
        review.setReviewText(reviewForm.getReviewText());

        Optional<Doctor> doctor = doctorRepository.findById(reviewForm.getDoctorId());
        doctor.ifPresent(review::setDoctor);

        try {
            reviewRepository.save(review);
        }catch (Exception e) {
            apiResponse.setMessage("Something went wrong");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable(value = "id") int id) {
        ApiResponse apiResponse = new ApiResponse();

        try{
            reviewRepository.deleteById(id);
        }catch (Exception e) {
            apiResponse.setMessage("Error. Try again later");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/reviews/{id}")
    public ResponseEntity<?> approveReview(@PathVariable(value = "id") int id) {
        ApiResponse apiResponse = new ApiResponse();

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        review.setShown(true);

        try{
            reviewRepository.save(review);
        }catch (Exception e) {
            apiResponse.setMessage("Could not save");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/reviews")
    public List<Review> getApprovedReviews() {
        return reviewRepository.findByIsShown(true);
    }
}
