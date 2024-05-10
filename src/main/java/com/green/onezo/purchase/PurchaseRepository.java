package com.green.onezo.purchase;

import com.green.onezo.enum_column.TakeInOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findById(Long purchaseId);


    List<Purchase> findByMemberId(Long memberId);

    List<Purchase> findByTotalPriceAndTakeInOut(int totalPrice, TakeInOut takeInOut);
}
