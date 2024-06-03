package com.green.onezo.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteStoreRepository extends JpaRepository<FavoriteStore, Long> {

    List<FavoriteStore> findByMemberId(Long memberId);

    Optional<FavoriteStore> findByMemberIdAndStoreId(Long MemberId,Long StoreId);
}