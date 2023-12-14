package com.example.demo.service;

import com.example.demo.domain.Review;
import com.example.demo.repository.ReviewRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewService() {
    }

    public Iterable<Review> getAllReviews() {
        return this.reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return this.reviewRepository.findById(id);
    }

    public Review addReview(Review review) {
        return (Review)this.reviewRepository.save(review);
    }

    public ResponseEntity<Review> updateReview(Long id, Review updatedReview) {
        Optional<Review> existingReviewOptional = this.reviewRepository.findById(id);
        if (existingReviewOptional.isPresent()) {
            Review existingReview = (Review)existingReviewOptional.get();
            existingReview.setDate(updatedReview.getDate());
            existingReview.setReview(updatedReview.getReview());
            existingReview.setDescription(updatedReview.getDescription());
            existingReview.setClient(updatedReview.getClient());
            existingReview.setMovie(updatedReview.getMovie());
            Review savedReview = (Review)this.reviewRepository.save(existingReview);
            return ResponseEntity.ok(savedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteReview(Long id) {
        this.reviewRepository.deleteById(id);
    }
}