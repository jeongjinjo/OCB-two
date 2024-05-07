package com.green.onezo.store;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

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
    public List<OrderType> getOrderType (OrderType orderType) {
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
}
