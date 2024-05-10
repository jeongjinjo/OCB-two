package com.green.onezo.purchase;

import com.green.onezo.cart.CartItem;
import com.green.onezo.cart.CartItemRepository;
import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;


    //    @Transactional
//    public Optional<Purchase> getPurchase(Long id) {
//        return purchaseRepository.findById(id);
//    }
    @Transactional
    public PurchaseDto getPurchase(PurchaseDto purchaseDto, Long purchaseId) {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            List<Purchase> purchaselist = purchaseRepository.findByMemberId(member.getId());
            if (purchaselist.isEmpty()) {
                CartItem cartItem = cartItemRepository
                Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
                purchase.setPurchaseId(purchaseDto.getId());
                purchase.setMember(member);
                return modelMapper.map(purchase, PurchaseDto.class);
            }
        }
        return null;
    }

    @Transactional
    public PurchaseDetailDto getDetail(Long id) {
        ModelMapper modelMapper = new ModelMapper();

        Optional<PurchaseDetail> get = purchaseDetailRepository.findById(id);

        if (get.isPresent()) {
            PurchaseDetail purchaseDetail = get.get();
            return modelMapper.map(purchaseDetail, PurchaseDetailDto.class);
        }

        return null;

    }

}
