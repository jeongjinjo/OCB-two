package com.green.onezo.purchase;

import com.green.onezo.member.Member;
import com.green.onezo.pay.Pay;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
@Tag(name = "purchase-controller", description = "메인페이지 (주문 조회/ 주문 상세 조회)")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Operation(summary = "주문 조회",
            description = "주문 조회"
    )
    @GetMapping("/record/{purchaseId}")

    public ResponseEntity<PurchaseDto> getRecord(@PathVariable Long purchaseId){
        PurchaseDto result = purchaseService.getPurchase(purchaseId);
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
}
