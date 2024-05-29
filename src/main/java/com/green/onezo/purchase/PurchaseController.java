package com.green.onezo.purchase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
@Tag(name = "purchase-controller", description = "메인페이지 (주문 조회/ 주문 상세 조회)")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Operation(summary = "주문 조회",
            description = "주문 조회"
    )
    @GetMapping("/record")
    public ResponseEntity<List<PurchaseDto>> getRecord(){
        List<PurchaseDto> result = purchaseService.getPurchase();
        if (result == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @Operation(summary = "주문 상세 조회",
            description = "주문 상세 조회"
    )
    @GetMapping("/detail/{id}")
    public ResponseEntity<PurchaseDetailDto> getDetail(Long id) {
        PurchaseDetailDto result = purchaseService.getDetail(id);
        if (result == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @Operation(summary = "현황 조회",
                description = "현황 조회")
    @GetMapping("/state/{id}")
    public ResponseEntity<PurchaseState> getPurchase(@PathVariable Long id){
        Optional<PurchaseState> purchaseState = purchaseService.getState(id);
        return purchaseState.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
