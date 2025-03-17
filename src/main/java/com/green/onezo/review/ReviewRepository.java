package com.green.onezo.review;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Cacheable(value = "review", key = "#memberId")
    List<Review> findByMemberId(Long memberId);
}
