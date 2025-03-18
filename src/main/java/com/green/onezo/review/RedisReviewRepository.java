package com.green.onezo.review;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisReviewRepository extends CrudRepository<RedisReview, String> {


}
