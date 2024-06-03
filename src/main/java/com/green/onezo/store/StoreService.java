package com.green.onezo.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.onezo.cart.CartRepository;
import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.schedule.ScheduleDto;
import com.green.onezo.schedule.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ObjectMapper objectMapper;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final FavoriteStoreRepository favoriteStoreRepository;
    private final ScheduleRepository scheduleRepository;


    // 매장상세 조회
    public StoreDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("스토어아이디" + storeId + "를 찾을 수 없습니다."));

        return StoreDto.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .addressOld(store.getAddressOld())
                .address(store.getAddress())
                .storeHours(store.getStoreHours())
                .storePhone(store.getStorePhone())
                .build();
    }

    //매장 식사 + 포장 여부
    public List<TakeInOut> getOrderType(TakeInOut orderType) {
        return storeRepository.findByOrderType(orderType);
    }

    //메장주소 리스트
    public List<StoreDto> storeDto() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(this::toStoreDto).collect(Collectors.toList());
    }


    public StoreDto toStoreDto(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .addressOld(store.getAddressOld())
                .build();
    }

    @Transactional
    public String insert() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        return user.getUsername();

    }

    public boolean getMemberCart(Long userId) {
        if (cartRepository.findById(userId) != null) {
            return false;
        } else {
            return true;
        }
    }

    //관심매장
    public FavoriteStoreDto addFavoriteStore(FavoriteStoreDto favoriteStoreDto) {

        ModelMapper modelMapper = new ModelMapper();
        //현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        //사용자 정보와 매장 정보가져오기
        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        Optional<Store> storeOptional = storeRepository.findById(favoriteStoreDto.getStoreId());

        if (memberOptional.isPresent() && storeOptional.isPresent()) {
            Member member = memberOptional.get();
            Store store = storeOptional.get();

                //관심매장이 등록 되있지 않은 경우 새로 등록
                FavoriteStore favoriteStore = new FavoriteStore();
                favoriteStore.setMember(member);
                favoriteStore.setStore(store);
                favoriteStore = favoriteStoreRepository.save(favoriteStore);

                return modelMapper.map(favoriteStore, FavoriteStoreDto.class);
            }

        return favoriteStoreDto;
    }


}
