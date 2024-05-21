package com.green.onezo.cart;

import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.menu.Menu;
import com.green.onezo.store.Store;
import com.green.onezo.store.StoreRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class CartControllerTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartDetailRepository cartDetailRepository;


//    @Test
//    @DisplayName("장바구니 생성")
//    void createCart() {
//        Member member = new Member();
//        member.setId(1L);
//
//        Store store = new Store();
//        store.setId(1L);
//
//        Cart cart = Cart.builder()
//                .member(member)
//                .store(store)
//                .takeInOut(TakeInOut.TAKE_OUT)
//                .build();
//
//        Cart savedCart = cartRepository.save(cart);
//        System.out.println(savedCart);
//
//        assertAll(
//                () -> assertNotNull(savedCart),
//                () -> assertNotNull(savedCart.getId()),
//                () -> assertEquals(1L, savedCart.getMember().getId()),
//                () -> assertEquals(1L, savedCart.getStore().getId()),
//                () -> assertEquals(TakeInOut.TAKE_OUT, savedCart.getTakeInOut())
//
//        );
//
//    }

    @Test
    @DisplayName("장바구니 담기")
    void addCart() {
        Member member = new Member();
        member.setId(1L);

        Menu menu = new Menu();
        menu.setId(1L);

        CartDetail cartDetail = CartDetail.builder()
                .quantity(1)
                .menu(menu)
                .build();

        CartDetail savedCartDetail = cartDetailRepository.save(cartDetail);
        System.out.println(savedCartDetail);

        assertAll(
                () -> assertNotNull(savedCartDetail),
                () -> assertNotNull(savedCartDetail.getId()),
                () -> assertEquals(1L, savedCartDetail.getMenu().getId()),
                () -> assertEquals(1, savedCartDetail.getQuantity())
        );
    }

    @Test
    @DisplayName("장바구니 조회")
    void getCart() {
        CartDto.CartRes cartRes = new CartDto.CartRes();
        cartRes.setCartId(1L);
        System.out.println(cartRes);

    }

    @Test
    @DisplayName("장바구니 상세 조회")
    void getCartDetail() {
     }

    @Test
    @DisplayName("장바구니 삭제")
    void deleteCart() {
    }

    @Test
    @DisplayName("장바구니 상세 삭제")
    void deleteCartDetail() {
    }
}