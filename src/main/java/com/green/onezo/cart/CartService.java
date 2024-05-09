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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final CartItemRepository cartItemRepository;
    private final CartDetailRepository cartDetailRepository;

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

//        Optional<CartItem> existingCartItem = cartItemRepository.findByMemberAndStoreAndMenu(member, store, menu);
//        if (existingCartItem.isPresent()) {
//            CartItem cartItem = existingCartItem.get();
//            return cartItemRepository.save(cartItem);
//        } else {
//            // 새로운 아이템 생성
//            CartItem cartItem = CartItem.builder()
//                    .member(member)
//                    .store(store)
//                    .build();
//            return cartItemRepository.save(cartItem);
//        }
        return null;
    }

    // 장바구니 조회
    public List<CartItemDto.CartItemRes> getCart(Long memberId) {
        return cartItemRepository.findByMemberId(memberId).stream()
                .map(item -> CartItemDto.CartItemRes.builder()
                        .cartItemId(item.getId())
                        .storeName(item.getStore().getStoreName())
                        //.menuImage(item.getMenu().getMenuImage())
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
        cartItemRepository.save(cartItem);
    }

    // 장바구니 아이템 삭제
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }


    @Transactional
    public CartItemDetailDto insert(CartItemDetailDto cartItemDetailDto) {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        Optional<Store> storeOptional = storeRepository.findById(cartItemDetailDto.getStoreId());
        if (memberOptional.isPresent() && storeOptional.isPresent()) {
            Member member = memberOptional.get();
            Store store = storeOptional.get();
            List<CartItem> cartItemlist = cartItemRepository.findByMemberId(member.getId());

            if(cartItemlist.size()<0) {
                CartItem cartItem = modelMapper.map(cartItemDetailDto, CartItem.class);
                cartItem.setMember(member);
                cartItem.setStore(store);
                cartItem = cartItemRepository.save(cartItem);
                return modelMapper.map(cartItem, CartItemDetailDto.class);
            }else{
                CartItem dbCart = cartItemlist.get(0);
                dbCart.setStore(store);
                dbCart = cartItemRepository.save(dbCart);
                return modelMapper.map(dbCart, CartItemDetailDto.class);
            }
        }
        return null;
    }
    @Transactional
    public CartDetailDto update(CartDetailDto cartDetailDto) {
        ModelMapper modelMapper = new ModelMapper();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//
//        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
//        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetailDto.getCartDetailId());
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartDetailDto.getCartId());
        if (cartItemOptional.isPresent()) {
            // 장바구니 있는 경우
//            CartItem cartItemList = cartItemRepository.findByMemberId(memberOptional.get().getId());
//            Long cartDetail = cartItemList.getId();
//            Menu menu = menuRepository.findById(
//                    cartDetailDto.getMenuId()).orElseThrow(
//                            () -> new EntityNotFoundException("아이디를 찾을 수 없습니다."));
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCartItem(cartItemOptional.get());
            cartDetail.setQuantity(cartDetailDto.getQuentity());
            Menu menu = menuRepository.findById(cartDetailDto.getMenuId()).orElseThrow(() -> new EntityNotFoundException("메뉴를 선택해주세요"));
            cartDetail.setMenu(menu);

            cartDetail = cartDetailRepository.save(cartDetail);

             modelMapper.map(cartDetail, CartDetailDto.class);

            // 장바구니 없는경우

        }

        return cartDetailDto;
    }



}



