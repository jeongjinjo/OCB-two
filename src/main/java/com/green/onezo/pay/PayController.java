package com.green.onezo.pay;


import com.green.onezo.pay.response.SingleResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/payment")
public class PayController {

//    https://api.tosspayments.com/v1/payments/confirm

    private final PayService payService;
    private final ResponseService responseService;
    @Operation(summary = "결제 요청 ",
            description = "order_id 얻오는것"
    )
    @PostMapping("")
    public PayRes requestPay(@ModelAttribute PayReq payReq) {
        try {
            return responseService.getSingleResult(
                    payService.requestPay(payReq)
            ).getData();
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "웹 페이지에서 요청한 값을 얻어온다.",
            description = "paymentkey 를 얻을수 있다."
    )
    @GetMapping("/success")
    public ResponseEntity<JSONObject> requestFinalPayments(@RequestParam String paymentKey,
                                                           @RequestParam String orderId,
                                                           @RequestParam Long amount){
        try {
            System.out.println("paymentKey= " + paymentKey);
            System.out.println("orderId= "+ orderId);
            System.out.println("amount= " + amount);
            payService.verifyRequest(paymentKey, orderId, amount);
             ResponseEntity<JSONObject> result = payService.requestFinalPayment(paymentKey, orderId, amount);
            return result;
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }

    }
    @Operation(summary = "결제 환불",
            description = "결제 환불사유 "
    )
    @GetMapping("/fail")
    public SingleResult<PaymentResHandleFailDto> requestFinalPaymentsFail(@RequestParam String errorCode,
                                                                          @RequestParam String orderId,
                                                                          @RequestParam String errorMsg){
        try {
            return responseService.getSingleResult(
                    payService.requestFail(errorCode, errorMsg, orderId)
            );
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
