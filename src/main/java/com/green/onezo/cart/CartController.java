package com.green.onezo.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "cart-controller", description = "장바구니")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    @Operation(summary = "장바구니 아이템 추가", description = "요청 데이터: 멤버 PK, 스토어 PK, 메뉴 PK, 수량")
    public ResponseEntity<String> addCartItem(@RequestBody @Valid CartItemDto.CartItemReq cartItemReq) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            CartItem cartItem = cartService.addCartItem(cartItemReq);
            return ResponseEntity.ok("장바구니에 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 추가 실패: " + e.getMessage());
        }
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "회원 장바구니 조회", description = "특정 회원의 장바구니 아이템을 조회합니다.")
    public ResponseEntity<List<CartItemDto.CartItemRes>> getCartItem(@Parameter(description = "멤버 PK", required = true) @PathVariable Long memberId) {
        List<CartItemDto.CartItemRes> cartItems = cartService.getCart(memberId);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update/{cartItemId}/{quantity}")
    @Operation(summary = "장바구니 아이템 수량 업데이트", description = "장바구니 아이템의 수량을 업데이트합니다.")
    public ResponseEntity<String> updateCartItem(@Parameter(description = "카트 아이템 PK", required = true) @PathVariable Long cartItemId, @Parameter(description = "수량", required = true) @PathVariable int quantity) {
        try {
            cartService.updateCartItem(cartItemId, quantity);
            return ResponseEntity.ok("장바구니 아이템 수량이 업데이트되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니 아이템을 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{cartItemId}")
    @Operation(summary = "장바구니 아이템 삭제", description = "장바구니 아이템을 삭제합니다.")
    public ResponseEntity<String> deleteCartItem(@Parameter(description = "카트 아이템 PK", required = true) @PathVariable Long cartItemId) {
        try {
            cartService.deleteCartItem(cartItemId);
            return ResponseEntity.ok("장바구니 아이템을 삭제했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니 아이템을 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러: " + e.getMessage());
        }
    }















}