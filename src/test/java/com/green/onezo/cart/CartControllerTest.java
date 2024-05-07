package com.green.onezo.cart;

import com.green.onezo.member.Member;
import com.green.onezo.menu.Menu;
import com.green.onezo.store.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartControllerTest {

    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    @DisplayName("장바구니 아이템 추가")
    void addCart() {
        Member member = new Member();
        member.setId(1L);

        Store store = new Store();
        store.setId(1L);

        Menu menu = new Menu();
        menu.setId(1L);

        CartItem cartItem = CartItem.builder()
                .member(member)
                .store(store)
                .menu(menu)
                .quantity(1)
                .build();

        CartItem savedItem = cartItemRepository.save(cartItem);
        System.out.println(savedItem);

        assertAll(
                () -> assertNotNull(savedItem),
                () -> assertNotNull(savedItem.getId()),
                () -> assertEquals(1L, savedItem.getMember().getId()),
                () -> assertEquals(1L, savedItem.getStore().getId()),
                () -> assertEquals(1L, savedItem.getMenu().getId()),
                () -> assertEquals(1, savedItem.getQuantity())
        );

    }

    @Test
    @DisplayName("장바구니 조회")
    void getCart() {
        List<CartItem> list = cartItemRepository.findByMemberId(1L);
        list.forEach(cartItem -> {
            System.out.println(cartItem.getId());
            System.out.println(cartItem.getStore());
            System.out.println(cartItem.getMenu());
            System.out.println(cartItem.getQuantity());
        });
    }
}
