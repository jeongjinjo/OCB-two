package com.green.onezo.cart;

import com.green.onezo.enum_column.TakeInOut;
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


    // 장바구니 생성
    @Operation(summary = "장바구니 생성 API",
    description = "로그인한 유저가 매장/포장 여부 선택 시, 장바구니 테이블에 멤버 pk, 매장 id, 포장여부가 insert 됩니다.")
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

    // 장바구니 담기
    @Operation(summary = "장바구니 담기 API",
            description = "메뉴를 담을 때, 장바구니 상세 테이블에 메뉴 id와 수량이 저장됩니다.")
    @PostMapping("/add")
    public ResponseEntity<String> addCart(@RequestBody CartDto.CartDetail cartDetailDto, Principal principal){
        String userId = principal.getName();
        Optional<Member> member = memberRepository.findByUserId(userId);
        Long memberId = member.get().getId();

        CartDto.CartDetail result = cartService.addCart(cartDetailDto, memberId);
        if (result == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니를 찾을 수 없습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("장바구니에 입력되었습니다.");

    }


    // 장바구니 조회
    @GetMapping("/get")
    @Operation(summary = "장바구니 조회 API", 
            description = "유저가 선택한 지점명과 주소, 포장여부를 조회합니다.")
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

    // 포장 여부 수정
    @PutMapping("/take")
    @Operation(summary = "포장 여부 수정 API", description = "TAKE_OUT/ DINE_IN 수정" )
    public ResponseEntity<CartDto.TakeInOutDto> updateTakeInOut(Principal principal, @RequestBody @Valid CartDto.TakeInOutDto takeInOutDto) {
        String userId = principal.getName();
        Optional<Member> member = memberRepository.findByUserId(userId);
        Long memberId = member.get().getId();

        CartDto.TakeInOutDto result = cartService.takeInOutUpdate(memberId, takeInOutDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 장바구니 삭제
    @DeleteMapping("/delete")
    @Operation(summary = "장바구니 삭제 API", description = "장바구니를 삭제합니다.")
    public ResponseEntity<String> deleteCart(Principal principal) {
        String userId = principal.getName();
        Optional<Member> member = memberRepository.findByUserId(userId);
        Long memberId = member.get().getId();

        try {
            cartService.deleteCart(memberId);
            return ResponseEntity.ok("장바구니를 초기화 했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러" + e.getMessage());
        }

    }


    // 장바구니 상세 삭제
    @DeleteMapping("/detail/delete/{cartDetailId}")
    @Operation(summary = "장바구니 상세 삭제 API", description = "장바구니 상세를 삭제합니다.")
    public ResponseEntity<String> deleteCartDetail(@Parameter(description = "장바구니 상세 ID", required = true) @PathVariable Long cartDetailId,
                                                 Principal principal) {
        String userId = principal.getName();
        Optional<Member> member = memberRepository.findByUserId(userId);
        Long memberId = member.get().getId();

        try {
            cartService.deleteCartDetail(cartDetailId, memberId);
            return ResponseEntity.ok("장바구니 상세를 삭제했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니 상세를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러" + e.getMessage());
        }

    }
}