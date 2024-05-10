package com.green.onezo.cart;

import com.green.onezo.member.Member;
import com.green.onezo.menu.Menu;
import com.green.onezo.store.Store;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    //select * from cart where member_id = ?;
    List<CartItem> findByMemberId(Long memberId);

//    Optional<CartItem> findByMemberAndStoreAndMenu(Member member, Store store, Menu menu);
    Optional<CartItem> findById(Long id);


    Optional<CartItem> findByMemberIdAndStoreId(Long memberId, Long storeId);

    @Query("SELECT c FROM CartItem c WHERE c.member.id = :memberId")
    Optional<CartItem> findCartItemByMemberId(Long memberId);
}
