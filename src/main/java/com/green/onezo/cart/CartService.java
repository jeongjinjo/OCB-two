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
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final CartItemRepository cartItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // 장바구니 아이템 추가
    public CartItem addCartItem(CartItemDto.CartItemReq cartItemReq) {

        Member member = memberRepository.findById(cartItemReq.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("멤버 아이디를 찾을 수 없습니다."));
        Store store = storeRepository.findById(cartItemReq.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("스토어 아이디를 찾을 수 없습니다."));
        Menu menu = menuRepository.findById(cartItemReq.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("메뉴 아이디를 찾을 수 없습니다."));
//        Menu menu = menuRepository.findByMenuImage(cartItemReq.getMenuImage())
//                .orElseThrow(() -> new IllegalArgumentException("메뉴 이미지를 찾을 수 없습니다."));

        if (menu.getStock().equals("Out of stock")) {
            throw new RuntimeException("재고가 부족합니다.");
        }

        Optional<CartItem> existingCartItem = cartItemRepository.findByMemberAndStoreAndMenu(member, store, menu);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemReq.getQuantity()); // 기존 수량에 추가
            return cartItemRepository.save(cartItem);
        } else {
            // 새로운 아이템 생성
            CartItem cartItem = CartItem.builder()
                    .member(member)
                    .store(store)
                    .menu(menu)
                    .quantity(cartItemReq.getQuantity())
                    .build();
            return cartItemRepository.save(cartItem);
        }
    }

    // 장바구니 조회
    public List<CartItemDto.CartItemRes> getCart(Long memberId) {
        return cartItemRepository.findByMemberId(memberId).stream()
                .map(item -> CartItemDto.CartItemRes.builder()
                        .cartItemId(item.getId())
                        .storeName(item.getStore().getStoreName())
                        .menuName(item.getMenu().getMenuName())
                        //.menuImage(item.getMenu().getMenuImage())
                        .quantity(item.getQuantity())
                        .price(item.getMenu().getPrice())
                        .address(item.getStore().getAddress())
                        .build())
                .collect(Collectors.toList());
    }

    ;

    // 장바구니 아이템 업데이트
    @Transactional
    public void updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 장바구니 아이템을 찾을 수 없습니다."));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    // 장바구니 아이템 삭제
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }


    @Transactional
    public CartItemDetailDto insert(CartItemDetailDto cartItemDetailDto, Long storeId) {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        System.out.println(user);

        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        Optional<Store> storeOptional = storeRepository.findById(storeId);
        if (memberOptional.isPresent() && storeOptional.isPresent()) {
            Member member = memberOptional.get();
            Store store = storeOptional.get();

            CartItem cartItem = modelMapper.map(cartItemDetailDto, CartItem.class);
            cartItem.setMember(member);
            cartItem.setStore(store);

            cartItem = cartItemRepository.save(cartItem);
            return modelMapper.map(cartItem, CartItemDetailDto.class);
        }
        return null;
    }


    @Transactional
    public CartMenuDto update(CartMenuDto cartMenuDto) {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Member> member = memberRepository.findByUserId(user.getUsername());
        if (member.isPresent()) {
            Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartMenuDto.getCartItemId());
            if (cartItemOptional.isPresent()) {
                CartItem cartItem = cartItemOptional.get();
                Long menuId = cartMenuDto.getMenuId();
                Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException("아이디를 찾을 수 없습니다."));
                cartItem.setMenu(menu);
                cartItem.setQuantity(cartMenuDto.getQuantity());

                cartItemRepository.save(cartItem);

                return modelMapper.map(cartItem, CartMenuDto.class);

//                cartItem = cartItemRepository.save(cartItem);
//
//                return modelMapper.map(cartItem, CartMenuDto.class);
            }
        }

        return cartMenuDto;

    }

}



