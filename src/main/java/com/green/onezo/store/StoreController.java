package com.green.onezo.store;

import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Tag(name = "store-controller", description = "매장")
public class StoreController {
    private final StoreService storeService;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final FavoriteStoreRepository favoriteStoreRepository;


    @Operation(summary = "매장 상세조회")
    @GetMapping("/detail/{storeId}")
    public ResponseEntity<StoreDto> getStoreById(
            @Parameter(description = "매장 ID", required = true)
            @PathVariable Long storeId) {

        StoreDto storeDto = storeService.getStoreById(storeId);
        return new ResponseEntity<>(storeDto, HttpStatus.OK);
    }

    //매장 식사 + 포장 여부
    @PostMapping("/orderType")
    public ModelAndView orderType(@RequestParam("orderType") TakeInOut orderType) {
        try {
            storeRepository.findByOrderType(orderType);
            ModelAndView modelAndView = new ModelAndView("주문 성공");
            modelAndView.addObject("Order", orderType);
            modelAndView.addObject("message", "주문이 성공적으로 완료 되었습니다.");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("주문 실패");
            modelAndView.addObject("error", "주문 처리 중 오류가 발생 했습니다.");
            return modelAndView;
        }
    }


    //매장주소 리스트
    @Operation(summary = "매장주소 리스트")
    @GetMapping("/storeList")
    public ResponseEntity<List<StoreDto>> getStoreAddress() {
        List<StoreDto> storeDtoList = storeService.storeDto();

        if (storeDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(storeDtoList);
    }

    @PostMapping("/insert")
    public String insertStore() {
        storeService.insert();
        return "success";
    }

    // 장바구니에 유저가 있는지 확인
    @Operation(summary = "로그인한테 유저가 장바구니가 있는지 확인")
    @GetMapping("/checkMem")
    public boolean getCartMember(Principal principal) {
        String userId = principal.getName();
        Optional<Member> member = memberRepository.findByUserId(userId);
        Long memberId = member.get().getId();
        if (storeService.getMemberCart(memberId) == false) {
            return false;
        } else {
            return true;
        }
    }

    @Operation(summary = "유저 아이디로 관심매장 적용")
    @PostMapping("/favoriteStore")
    public ResponseEntity<FavoriteFindDto> addFavoriteStore(@RequestBody FavoriteFindDto favoriteFindDto) {
        FavoriteFindDto saveFavoriteStore = storeService.addFavoriteStore(favoriteFindDto);
        System.out.println(saveFavoriteStore);
        return ResponseEntity.ok(saveFavoriteStore);
    }


    @Operation(summary = "관심매장 조회")
    @GetMapping("/list")
    public ResponseEntity<List<FavoriteStoreDto>> getFavoriteStores() {
        List<FavoriteStoreDto> favoriteStores = storeService.selectStore();
        return ResponseEntity.ok(favoriteStores);
    }
}