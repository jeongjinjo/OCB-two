package com.green.onezo.store;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findStoreById(Long id);

    List<OrderType> findByOrderType(OrderType orderType);


    List<Store> findByIdAndStoreNameAndAddressAndAddressOld(Long id, String storeName, String address, String addressOld);


}