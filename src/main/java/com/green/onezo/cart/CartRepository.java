package com.green.onezo.cart;

import com.green.onezo.enum_column.TakeInOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMemberId(Long memberId);

    @Query("SELECT c FROM Cart c WHERE c.member.id = :memberId")
    Optional<Cart> findCartByMemberId(Long memberId);


}
