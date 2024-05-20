package com.green.onezo.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMemberId(Long memberId);

    Optional<Cart> findById(Long id);

    @Query("SELECT c FROM Cart c WHERE c.member.id = :memberId")
    Optional<Cart> findCartByMemberId(Long memberId);

}
