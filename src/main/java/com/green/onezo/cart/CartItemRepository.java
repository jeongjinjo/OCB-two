package com.green.onezo.cart;

import com.green.onezo.member.Member;
import com.green.onezo.menu.Menu;
import com.green.onezo.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByMemberId(Long memberId);

    Optional<CartItem> findByMemberAndStoreAndMenu(Member member, Store store, Menu menu);

    Optional<CartItem> findById(Long id);

}
