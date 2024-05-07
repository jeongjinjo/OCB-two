package com.green.onezo.purchase;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PurchaseControllerTest {

    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    PurchaseDetailRepository purchaseDetailRepository;


    @ParameterizedTest
    @DisplayName("구매내역 조회")
    @ValueSource(longs = {1L})
    void getRecord(Long id) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setId(id);
        System.out.println(purchaseDto);
    }

    @ParameterizedTest
    @DisplayName("구매 상세 내역 조회")
    @ValueSource(longs = {1L})
    void getDetail(Long id) {
        PurchaseDetailDto purchaseDetailDto = new PurchaseDetailDto();
        purchaseDetailDto.setId(id);
        System.out.println(id);
    }
}