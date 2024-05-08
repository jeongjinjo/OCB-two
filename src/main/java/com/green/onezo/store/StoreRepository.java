package com.green.onezo.store;

import com.green.onezo.enum_column.TakeInOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findById(Long id);

    List<TakeInOut> findByOrderType(TakeInOut orderType);



}