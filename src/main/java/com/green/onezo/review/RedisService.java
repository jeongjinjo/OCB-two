package com.green.onezo.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisReviewRepository redisReviewRepository;

    public RedisReview saveRedisReview(String id, String comment, int star){
        RedisReview review = RedisReview.builder()
                .id(id)
                .comment(comment)
                .star(star)
                .build();
        return redisReviewRepository.save(review);
    }
}
