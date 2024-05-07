package com.green.onezo.purchase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;


    //    @Transactional
//    public Optional<Purchase> getPurchase(Long id) {
//        return purchaseRepository.findById(id);
//    }
    @Transactional
    public PurchaseDto getPurchase(Long purchaseId) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Purchase> get = purchaseRepository.findById(purchaseId);
        if (get.isPresent()) {
            Purchase purchase = get.get();
            return modelMapper.map(purchase, PurchaseDto.class);
        } else
            return new PurchaseDto();
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
