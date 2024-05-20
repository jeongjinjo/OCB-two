package com.green.onezo.cart;

import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "cart-controller", description = "장바구니")
public class CartController {
    private final CartService cartService;
    private final MemberRepository memberRepository;


    @Operation(summary = "장바구니 생성 API",
    description = "로그인한 유저가 매장/포장 여부 선택 시, 장바구니 테이블에 멤버 pk, 매장 id, 포장여부가 insert 된다.")
    @PostMapping("")
    public ResponseEntity<String> createCart(@RequestBody @Valid CartDto.Cart cartDto,
                                         Authentication authentication){
        User user =  (User)authentication.getPrincipal();
        if (user == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("JWT 로그인이 필요합니다.");
        }
        CartDto.Cart result = cartService.createCart(cartDto);
        if (result == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("매장을 선택해 주세요");
        }
        return ResponseEntity.status(HttpStatus.OK).body("장바구니에 입력되었습니다.");
    }

    @Operation(summary = "장바구니 담기 API",
            description = "메뉴를 담을 때, 장바구니 상세 테이블에 메뉴 id와 수량이 저장된다.")
    @PostMapping("/add")
    public ResponseEntity<String> addCart(@RequestBody CartDto.CartDetail cartDetailDto, Principal principal){
        String userId = principal.getName();
        Optional<Member> member = memberRepository.findByUserId(userId);
        Long memberId = member.get().getId();

        CartDto.CartDetail result = cartService.addCart(cartDetailDto, memberId);
        if (result == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니 아이템을 찾을 수 없습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("업데이트 되었습니다.");

    }


    // 장바구니 조회
    @GetMapping("/get")
    @Operation(summary = "장바구니 조회 API", description = "로그인한 유저가 선택한 지점명과 주소, 포장여부를 조회합니다.")
    public ResponseEntity<CartDto.CartRes> getCart(Principal principal) {
        String userId = principal.getName();
        Optional<Member> member = memberRepository.findByUserId(userId);
        Long memberId = member.get().getId();

        CartDto.CartRes result = cartService.getCart(memberId);
        if (result == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니를 찾을 수 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 장바구니 상세 조회
    @GetMapping("/get/detail")
    @Operation(summary = "장바구니 상세 조회 API",
            description = "유저의 장바구니에 담긴 각 아이템의 메뉴명, 수량, 가격, 메뉴 이미지를 얻어옵니다.")
    public ResponseEntity<List<CartDto.CartDetailRes>> getCartDetail(Principal principal) {
        String userId = principal.getName();
        Optional<Member> member = memberRepository.findByUserId(userId);
        Long memberId = member.get().getId();

        List<CartDto.CartDetailRes> result = cartService.getCartDetail(memberId);
        if (result == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니를 찾을 수 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    @DeleteMapping("/reset/{cartItemId}")
//    @Operation(summary = "장바구니 초기화", description = "장바구니를 삭제합니다.")
//    public ResponseEntity<String> deleteCart(@Parameter(description = "카트 아이템 pk", required = true)
//                                             @PathVariable Long cartItemId) {
//        try {
//            cartService.deleteCart(cartItemId);
//            return ResponseEntity.ok("장바구니를 초기화 했습니다.");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니를 찾을 수 없습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러: " + e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/delete/{cartDetailId}")
//    @Operation(summary = "장바구니 아이템 삭제", description = "장바구니 아이템을 삭제합니다.")
//    public ResponseEntity<String> deleteCartItem(@Parameter(description = "카트 디테일 pk", required = true)
//                                                 @PathVariable Long cartDetailId) {
//        try {
//            cartService.deleteCartItem(cartDetailId);
//            return ResponseEntity.ok("장바구니 아이템을 삭제했습니다.");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니 아이템을 찾을 수 없습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러: " + e.getMessage());
//        }
//    }


}