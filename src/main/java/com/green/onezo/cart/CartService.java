package com.green.onezo.cart;

import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.menu.Menu;
import com.green.onezo.menu.MenuRepository;
import com.green.onezo.store.Store;
import com.green.onezo.store.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // 장바구니 생성
    @Transactional
    public CartDto.Cart createCart(CartDto.Cart cartDto) {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        Optional<Store> storeOptional = storeRepository.findById(cartDto.getStoreId());
        if (memberOptional.isPresent() && storeOptional.isPresent()) {
            Member member = memberOptional.get();
            Store store = storeOptional.get();
            List<Cart> cartlist = cartRepository.findByMemberId(member.getId());

            if (cartlist.isEmpty()) {
                Cart cart = modelMapper.map(cartDto, Cart.class);
                cart.setMember(member);
                cart.setStore(store);
                cart = cartRepository.save(cart);
                return modelMapper.map(cart, CartDto.Cart.class);
            } else {
                Cart dbCart = cartlist.get(0);
                dbCart.setStore(store);
                dbCart = cartRepository.save(dbCart);
                return modelMapper.map(dbCart, CartDto.Cart.class);
            }
        }
        return null;
    }


    // 장바구니 담기 // 수량업뎃
    @Transactional
    public CartDto.CartDetail addCart(CartDto.CartDetail cartDetailDto, Long memberId) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Cart> cartOptional = cartRepository.findCartByMemberId(memberId);
        if (cartOptional.isPresent()) {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cartOptional.get());
            cartDetail.setQuantity(cartDetailDto.getQuentity());
            Menu menu = menuRepository.findById(cartDetailDto.getMenuId()).orElseThrow(() -> new EntityNotFoundException("메뉴를 선택해주세요"));
            cartDetail.setMenu(menu);
            cartDetail = cartDetailRepository.save(cartDetail);
            return modelMapper.map(cartDetail, CartDto.CartDetail.class);
        }

        return null;
    }


    // 장바구니 조회
    @Transactional
    public CartDto.CartRes getCart(Long memberId) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Cart> cartOpt = cartRepository.findCartByMemberId(memberId);

        if(cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            Store store = cart.getStore();
            return new CartDto.CartRes(store.getStoreName(),store.getAddress(), cart.getTakeInOut());
        } else  {
            throw new EntityNotFoundException("장바구니를 찾을 수 없습니다: " + memberId);
        }
    }

//    // 장바구니 상세 조회
//    @Transactional
//    public List<CartDto.CartDetailRes> getCartDetail(Long memberId) {
//
//        ModelMapper modelMapper = new ModelMapper();
//        List<CartDetail> cartDetails = cartRepository.findCartByMemberId(memberId);
//
//        if (cartDetails.isEmpty()) {
//            throw new EntityNotFoundException("Cart details not found for memberId: " + memberId);
//        }
//
//        return cartDetails.stream()
//                .map(cartDetail -> {
//                    Menu menu = cartDetail.getMenu();
//                    return new CartDto.CartDetailRes(
//                            cartDetail.getId(),
//                            menu.getMenuName(),
//                            cartDetail.getQuantity(),
//                            menu.getPrice(),
//                            menu.getMenuImage()
//                    );
//                })
//                .toList();
//    }

//    // 장바구니 전체 삭제
//    public void deleteCart(Long cartItemId) {
//        cartRepository.deleteById(cartItemId);
//    }
//
//    // 장바구니 아이템 개별 삭제
//    public void deleteCartItem(Long cartDetailId) {
//        cartDetailRepository.deleteById(cartDetailId);
//    }

}




