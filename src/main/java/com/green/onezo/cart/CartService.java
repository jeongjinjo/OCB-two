package com.green.onezo.cart;

import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.menu.Menu;
import com.green.onezo.menu.MenuRepository;
import com.green.onezo.store.Store;
import com.green.onezo.store.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
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
                Cart existingCart = cartlist.get(0);
                existingCart.setStore(store);
                existingCart.setTakeInOut(cartDto.getTakeInOut());
                existingCart = cartRepository.save(existingCart);
                return modelMapper.map(existingCart, CartDto.Cart.class);
            }
        }
        return null;
    }


    // 장바구니 담기
    @Transactional
    public CartDto.CartDetail addCart(CartDto.CartDetail cartDetailDto, Long memberId) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Cart> cartOptional = cartRepository.findCartByMemberId(memberId);

            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                Menu menu = menuRepository.findById(cartDetailDto.getMenuId()).orElseThrow(() -> new EntityNotFoundException("메뉴를 선택해주세요"));

                Optional<CartDetail> cartDetailOptional = cartDetailRepository.findByCartIdAndMenuId(cart.getId(), menu.getId());

                CartDetail cartDetail;
                if (cartDetailOptional.isPresent()) {
                    cartDetail = cartDetailOptional.get();
                    cartDetail.setQuantity(cartDetail.getQuantity() + cartDetailDto.getQuentity());
                } else {
                    cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setQuantity(cartDetailDto.getQuentity());
                    cartDetail.setMenu(menu);
                }

                cartDetail = cartDetailRepository.save(cartDetail);
                return modelMapper.map(cartDetail, CartDto.CartDetail.class);

            }

        return null;
    }


    // 장바구니 조회
    @Transactional
    public CartDto.CartRes getCart(Long memberId) {
        Optional<Cart> cartOpt = cartRepository.findCartByMemberId(memberId);

        if(cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            Store store = cart.getStore();
            return new CartDto.CartRes(cart.getId(), store.getStoreName(), store.getAddress(), cart.getTakeInOut());
        } else  {
            throw new EntityNotFoundException("장바구니를 찾을 수 없습니다.");
        }
    }

    // 장바구니 상세 조회
    @Transactional
    public List<CartDto.CartDetailRes> getCartDetail(Long memberId) {
        List<CartDetail> cartDetails = cartDetailRepository.findByMemberId(memberId);

        if (cartDetails.isEmpty()) {
            throw new EntityNotFoundException("장바구니를 찾을 수 없습니다.");
        }

        return cartDetails.stream()
                .map(cartDetail -> {
                    Menu menu = cartDetail.getMenu();
                    return new CartDto.CartDetailRes(
                            cartDetail.getId(),
                            menu.getMenuName(),
                            cartDetail.getQuantity(),
                            menu.getPrice(),
                            menu.getMenuImage()
                    );
                })
                .toList();
    }

    // 포장 여부 수정
    @Transactional
    public CartDto.TakeInOutDto takeInOutUpdate(Long memberId, CartDto.TakeInOutDto takeInOutDto) {
        Optional<Cart> cartOpt = cartRepository.findCartByMemberId(memberId);

        if(cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.setTakeInOut(takeInOutDto.getTakeInOut());
            return new CartDto.TakeInOutDto(cart.getTakeInOut());
        } else  {
            throw new EntityNotFoundException("장바구니를 찾을 수 없습니다.");
        }
    }


    // 장바구니 삭제
    public void deleteCart(Long memberId) {
        Optional<Cart> cartOptional = cartRepository.findCartByMemberId(memberId);

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cartDetailRepository.deleteAll(cart.getCartDetails());
            cartRepository.delete(cart);
        } else {
            throw new EntityNotFoundException("장바구니를 찾을 수 없습니다.");
        }
    }

    // 장바구니 상세 삭제
    @Transactional
    public void deleteCartDetail(Long cartDetailId, Long memberId) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetailId);

        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();
            Cart cart = cartDetail.getCart();

            if (!cart.getMember().getId().equals(memberId)) {
                throw new EntityNotFoundException("장바구니를 찾을 수 없습니다.");
            }
            cartDetailRepository.deleteById(cartDetailId);
        } else {
            throw new EntityNotFoundException("장바구니를 찾을 수 없습니다.");
        }
    }

}




