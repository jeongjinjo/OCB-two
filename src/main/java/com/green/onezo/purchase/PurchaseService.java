package com.green.onezo.purchase;

import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final MemberRepository memberRepository;


    //    @Transactional
//    public Optional<Purchase> getPurchase(Long id) {
//        return purchaseRepository.findById(id);
//    }
    @Transactional
    public List<PurchaseDto> getPurchase() {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        List<PurchaseDto> purchaseDtoList = new ArrayList<>();
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            List<Purchase> purchaselist = purchaseRepository.findByMemberId(member.getId());
            if (!purchaselist.isEmpty()) {
                for (Purchase purchase : purchaselist) {
                    PurchaseDto purchaseDto = modelMapper.map(purchase, PurchaseDto.class);
                    purchaseDtoList.add(purchaseDto);
                }
            }
        }

        return purchaseDtoList;
    }

    @Transactional
    public PurchaseDetailDto getDetail(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            List<Purchase> purchaselist = purchaseRepository.findByMemberId(member.getId());
            if (!purchaselist.isEmpty()) {
                Optional<PurchaseDetail> get = purchaseDetailRepository.findById(id);
                if (get.isPresent()) {
                    PurchaseDetail purchaseDetail = get.get();
                    return modelMapper.map(purchaseDetail, PurchaseDetailDto.class);
                }
            }
        }
        return null;
    }

}
