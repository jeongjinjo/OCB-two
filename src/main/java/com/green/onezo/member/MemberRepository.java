package com.green.onezo.member;

import com.green.onezo.enum_column.ResignYn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);

    Optional<Member> findByUserIdAndPassword(String userId, String password);

    Optional<Member> findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByPhone(String phone);

    Optional<Member> findByNameAndPhone(String name, String phone);

    Optional<Member> findByUserIdAndNameAndPhone(String userId, String name, String phone);

    Optional<Member> findByIdAndResignYn(Long id, ResignYn resignYn);

    Optional<Member> findByNameAndPhoneAndResignYn(String name, String phone, ResignYn resignYn);

    Optional<Member> findByUserIdAndNameAndPhoneAndResignYn(String userId, String name, String phone, ResignYn resignYn);

    Optional<Member> findByUserIdAndResignYn(String userId, ResignYn resignYn);

    Optional<Member> findByNicknameAndResignYn(String nickname, ResignYn resignYn);

    Optional<Member> findByPhoneAndResignYn(String phone, ResignYn resignYn);
}