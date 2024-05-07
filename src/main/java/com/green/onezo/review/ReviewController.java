//package com.green.onezo.review;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/review")
//@RequiredArgsConstructor
//@Tag(name = "Review-Controller", description = "리뷰")
//public class ReviewController {
//
//    private final ReviewService reviewService;
//
//    @GetMapping("/{storeId}")
//    public ResponseEntity<List<Review>> getReviews(@PathVariable Long storeId) {
//        List<Review> reviews = reviewService.StoreReviews(storeId);
//        return ResponseEntity.ok(reviews);
//    }
//}
