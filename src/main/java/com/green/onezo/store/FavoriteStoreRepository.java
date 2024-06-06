package com.green.onezo.store;

import com.green.onezo.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteStoreRepository extends JpaRepository<FavoriteStore, Long> {

    List<FavoriteStore> findByMember(Member member);

    Optional<FavoriteStore> findByMemberIdAndStoreId(Long MemberId,Long StoreId);
}