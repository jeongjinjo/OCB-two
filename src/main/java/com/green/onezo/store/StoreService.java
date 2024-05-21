package com.green.onezo.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.onezo.cart.CartRepository;
import com.green.onezo.enum_column.TakeInOut;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ObjectMapper objectMapper;
    private final CartRepository cartRepository;


    // 매장상세 조회
    public StoreDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("스토어아이디?" + storeId));

        return StoreDto.builder()
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .storePhone(store.getStorePhone())
                .build();
    }
    //매장 식사 + 포장 여부
    public List<TakeInOut> getOrderType (TakeInOut orderType) {
        return storeRepository.findByOrderType(orderType);
    }

    //메장주소 리스트
    public List<StoreDto> storeDto(){
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(this::toStoreDto).collect(Collectors.toList());
    }


    public StoreDto toStoreDto(Store store){
        return StoreDto.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .addressOld(store.getAddressOld())
                .build();
    }

    @Transactional
    public String insert(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        return user.getUsername();

    }

    public boolean getMemberCart(Long userId) {
        if(cartRepository.findById(userId) != null){
            return false;
        }else{
            return true;
        }
    }
}
