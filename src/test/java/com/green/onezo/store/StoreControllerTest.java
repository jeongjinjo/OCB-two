package com.green.onezo.store;

import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.store.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreControllerTest {

    @Autowired
    StoreRepository storeRepository;

    @Test
    @DisplayName("매장리스트 보내주기")
    void storeList(){

        Store store = new Store();

        store.setStoreName("원조치킨 반월당점");
        store.setAddress("대구광역시 중구 중앙대로 394");
        store.setStoreHours("24시간");
        store.setAddressOld("주소 Test");
        store.setStorePhone("053-123-4567");
        store.setOrderType(TakeInOut.TAKE_OUT);

        storeRepository.save(store);

        System.out.println(store);

        Store storeList = storeRepository.findById(store.getId()).orElse(null);

        assertEquals(storeList.getStoreName(),store.getStoreName(),"매장을 찾을 수 없습니다.");
        assertEquals(storeList.getAddress(),store.getAddress(),"주소가 일치하지 않습니다.");
        assertEquals(storeList.getAddressOld(),store.getAddressOld(),"주소가 일치하지 않습니다.");
        assertEquals(storeList.getStorePhone(),store.getStorePhone(),"찾을 수 없는 번호입니다.");
        assertEquals(storeList.getStoreHours(),store.getStoreHours(),"일치하지 않습니다.");
        assertEquals(storeList.getOrderType(),store.getOrderType(),"포장여부를 선택 해주세요.");

    }

}