package com.green.onezo.store;

import com.green.onezo.enum_column.ResignYn;
import com.green.onezo.enum_column.Role;
import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.store.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Autowired
    private FavoriteStoreRepository favoriteStoreRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("관심매장 정보 보여주기")
    void addFavoriteStore(){
        String userId = "testUser" + UUID.randomUUID().toString();

        Member member = new Member();
        member.setUserId(userId);
        member.setName("정은희");
        member.setNickname("희야");
        member.setPassword("1234");
        member.setPhone("010-1234-2416");
        member.setResignYn(ResignYn.Y);
        member.setRole(Role.USER);
        member = memberRepository.save(member);

        Store store = new Store();
        store.setStoreName("원조치킨 반월당점");
        store.setAddress("Test");
        store.setAddressOld("TestOld");
        store.setStoreHours("24시간");
        store.setStorePhone("053-123-4567");
        store.setOrderType(TakeInOut.TAKE_OUT);
        store = storeRepository.save(store);

        FavoriteStore favoriteStore = new FavoriteStore();
        favoriteStore.setMember(member);
        favoriteStore.setStore(store);
        favoriteStoreRepository.save(favoriteStore);

        // 특정 회원의 관심 매장 리스트 조회
        Optional<FavoriteStore> favoriteStores = favoriteStoreRepository.findByMemberIdAndStoreId(member.getId(),store.getId());

        assertFalse(favoriteStores.isEmpty(),"관심매장이 등록되었습니다.");

//        FavoriteStore favoriteStoress = favoriteStores.get();
//        assertEquals(member.getId(), favoriteStoress.getMember().getId(), "회원 ID가 일치하지 않습니다.");
//        assertEquals(store.getId(), favoriteStoress.getStore().getId(), "매장 ID가 일치하지 않습니다.");
    }
}