package com.green.onezo.cart;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    Optional<CartDetail> findById(Long id);

    @Query("SELECT cd FROM CartDetail cd WHERE cd.cart.member.id = :memberId")
    List<CartDetail> findByMemberId(Long memberId);

    Optional<CartDetail> findByCartIdAndMenuId(Long cartId, Long menuId);
}
