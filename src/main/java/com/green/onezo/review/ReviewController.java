package com.green.onezo.review;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Tag(name = "Review-Controller", description = "리뷰")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/up")
    public ResponseEntity<ReviewDto> getReview(@RequestBody @Valid ReviewDto reviewDto, Authentication authentication){

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("JWT 로그인이 필요합니다.");
        }
        ReviewDto result = reviewService.getReview(reviewDto);

        if (result == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
