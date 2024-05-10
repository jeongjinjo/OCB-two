package com.green.onezo.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    Optional<CartDetail> findById(Long id);

    @Query("SELECT c FROM CartItem c WHERE c.member.id = :memberId")
    Optional<CartItem> findByCartItemId(Long memberId);
}
