package com.green.onezo.cart;

import com.green.onezo.global.error.BizException;
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

import java.util.ArrayList;
import java.util.Collections;
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

    @Transactional
    public CartItemDetailDto createCart(CartItemDetailDto cartItemDetailDto) {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        Optional<Store> storeOptional = storeRepository.findById(cartItemDetailDto.getStoreId());
        if (memberOptional.isPresent() && storeOptional.isPresent()) {
            Member member = memberOptional.get();
            Store store = storeOptional.get();
            List<CartItem> cartItemlist = cartItemRepository.findByMemberId(member.getId());

            if (cartItemlist.isEmpty()) {
                CartItem cartItem = modelMapper.map(cartItemDetailDto, CartItem.class);
                cartItem.setMember(member);
                cartItem.setStore(store);
                cartItem = cartItemRepository.save(cartItem);
                return modelMapper.map(cartItem, CartItemDetailDto.class);
            } else {
                CartItem dbCart = cartItemlist.get(0);
                dbCart.setStore(store);
                dbCart = cartItemRepository.save(dbCart);
                return modelMapper.map(dbCart, CartItemDetailDto.class);
            }
        }
        return null;
    }

    @Transactional
    public CartDetailDto addCart(CartDetailDto cartDetailDto, Long memberId) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<CartItem> cartItemOptional = cartItemRepository.findCartItemByMemberId(memberId);
        if (cartItemOptional.isPresent()) {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCartItem(cartItemOptional.get());
            cartDetail.setQuantity(cartDetailDto.getQuentity());
            Menu menu = menuRepository.findById(cartDetailDto.getMenuId()).orElseThrow(() -> new EntityNotFoundException("메뉴를 선택해주세요"));
            cartDetail.setMenu(menu);
            cartDetail = cartDetailRepository.save(cartDetail);
            return modelMapper.map(cartDetail, CartDetailDto.class);
        }

        return null;
    }


    // 장바구니 조회
    @Transactional
    public List<CartItemDto.CartRes> getCart(Long memberId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        if (!memberOptional.isPresent()) {
            throw new EntityNotFoundException("해당하는 사용자를 찾을 수 없습니다.");
        }
        Member member = memberOptional.get();

        List<CartItem> cartItems = cartItemRepository.findByMemberId(memberId);
        List<CartItemDto.CartRes> cartItemResponses = new ArrayList<>();
        for (CartItem item : cartItems) {
            for (CartDetail detail : item.getCartDetails()) {
                CartItemDto.CartRes cartItemRes = CartItemDto.CartRes.builder()
                        .storeName(item.getStore().getStoreName())
                        .address(item.getStore().getAddress())
                        .takeInOut(item.getTakeInOut())
                        .build();
                cartItemResponses.add(cartItemRes);
            }
        }
        return cartItemResponses;
    }

    // 장바구니 상세 조회
    @Transactional
    public List<CartItemDto.CartDetailRes> getCartDetail(Long memberId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        if (!memberOptional.isPresent()) {
            throw new EntityNotFoundException("해당하는 사용자를 찾을 수 없습니다.");
        }
        Member member = memberOptional.get();

        List<CartItem> cartItems = cartItemRepository.findByMemberId(member.getId());
        List<CartItemDto.CartDetailRes> cartItemResponses = new ArrayList<>();

        for (CartItem item : cartItems) {
            for (CartDetail detail : item.getCartDetails()) {
                CartItemDto.CartDetailRes cartDetailRes = CartItemDto.CartDetailRes.builder()
                        .cartDetailId(detail.getId())
                        .menuName(detail.getMenu().getMenuName())
                        .quantity(detail.getQuantity())
                        .price(detail.getMenu().getPrice())
                        .menuImage(detail.getMenu().getMenuImage())
                        .build();
                cartItemResponses.add(cartDetailRes);
            }
        }
        return cartItemResponses;
    }

    // 장바구니 전체 삭제
    public void deleteCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // 장바구니 아이템 개별 삭제
    public void deleteCartItem(Long cartDetailId) {
        cartDetailRepository.deleteById(cartDetailId);
    }

}




