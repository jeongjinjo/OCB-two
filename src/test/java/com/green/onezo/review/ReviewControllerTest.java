package com.green.onezo.review;

import com.green.onezo.enum_column.ResignYn;
import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.redis.RedisConfig;
import com.green.onezo.store.Store;
import com.green.onezo.store.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(RedisConfig.class)
class ReviewControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ReviewControllerTest.class);
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    RedisReviewRepository redisReviewRepository;

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

    @Test
    @DisplayName("ttl 테스트")
    void ttlTest() throws InterruptedException{
        RedisReview review = new RedisReview("1","테스트",1);
        redisReviewRepository.save(review);

//        Thread.sleep(2000);

        Optional<RedisReview> redisReview = redisReviewRepository.findById("1");

        assertThat(redisReview).isNotEmpty();

    }

}