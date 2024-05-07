package com.green.onezo.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByUserIdAndPassword(String userId, String password);

    Optional<Member> findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByPhone(String phone);

    Optional<Member> findByNameAndPhone(String name, String phone);

    Optional<Member> findByUserIdAndNameAndPhone(String userId, String name, String phone);
}