package com.green.onezo.review;

import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.store.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewControllerTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 확인")
    void getReview() {
        Review review = new Review();
        review.setStar(3);
        review.setComment("리뷰달기");
        reviewRepository.save(review);
        Review reviewList = reviewRepository.findById(review.getId()).orElse(null);
        assertEquals(reviewList.getReview(),review.getReview());
        assertEquals(reviewList.getStar(),review.getStar());
        System.out.println(reviewList);

    }

}