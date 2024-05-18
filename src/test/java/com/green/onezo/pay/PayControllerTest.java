package com.green.onezo.pay;

import com.green.onezo.member.Member;
import com.green.onezo.purchase.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PayControllerTest {

    @Autowired
    PayRepository payRepository;

    @Test
    @DisplayName("결제 요청")
    void requestPay() {
        Pay pay = new Pay();
        pay.setPayType(PayType.CARD);
        pay.setAmount(30000L);
        pay.setCustomerName(CustomerName.원조간장통닭);
        pay.setMember(Member.builder()
                        .userId("aaaa@naver.com")
                        .phone("010-1234-5678")
                        .nickname("adsadsa")
                .build());

        pay.setCreateDate(LocalDateTime.now());
        pay.setOrderId("qweadsafafdadsadf");
        pay.setPaySuccessYn("Y");
        pay.setUserId(pay.getMember().getUserId());
        payRepository.save(pay);

        Pay paylist = payRepository.findById(pay.getId()).orElse(null);
        Assertions.assertNotNull(paylist);
        Assertions.assertNotNull(PayType.CARD, paylist.getPayType().toString());
        Assertions.assertNotNull(30000L, paylist.getAmount().toString());
        Assertions.assertNotNull(CustomerName.원조간장통닭, paylist.getCustomerName().toString());
        Assertions.assertNotNull(pay.getUserId(), paylist.getUserId());
        Assertions.assertNotNull(paylist.getMember().getUserId());
        Assertions.assertNotNull(paylist.getMember().getNickname());
        Assertions.assertNotNull(paylist.getMember().getPhone());
        Assertions.assertNotNull(paylist.getCreateDate().toString());
        Assertions.assertNotNull(paylist.getOrderId());
        Assertions.assertNotNull(paylist.getPaySuccessYn());
    }

    @Test
    void requestFinalPayments() {


    }

    @Test
    void requestFinalPaymentsFail() {
    }
}