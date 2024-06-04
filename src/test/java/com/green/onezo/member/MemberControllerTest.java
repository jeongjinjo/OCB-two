package com.green.onezo.member;

import com.green.onezo.enum_column.ResignYn;
import com.green.onezo.global.error.BizException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberControllerTest {

//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    private MemberController memberController;
//
//    @Autowired
//    private MemberService memberService;
//
//
//
//    @Test
//    @DisplayName("회원 정보 조회")
//    void getMemberInfo() {
//
//        FindDto.InfoRes memberRes = new FindDto.InfoRes();
//        memberRes.setUserId("user123");
//        System.out.println(memberRes);
//
//        Member newMember = new Member();
//        newMember.setUserId("member123");
//        newMember.setPassword("password123");
//        newMember.setName("성함임");
//        newMember.setNickname("닉네임임");
//        newMember.setPhone("010-5555-5555");
//        memberRepository.save(newMember);
//
//        FindDto.InfoRes infoRes = new FindDto.InfoRes();
//        infoRes.setUserId(newMember.getUserId());
//        System.out.println(newMember);
//
//        assertEquals("member123", infoRes.getUserId());
//        assertEquals("성함임", infoRes.getName());
//        assertEquals("닉네임임", infoRes.getNickname());
//        assertEquals("010-5555-5555", infoRes.getPhone());
//
//    }
//
//    @Test
//    @DisplayName("회원 정보 수정")
//    void updateMember() {
//
//        Member member = new Member();
//        member.setUserId("member123");
//        member.setPassword("password123");
//        newMember.setName("성함임");
//        newMember.setNickname("닉네임임");
//        newMember.setPhone("010-5555-5555");
//        memberRepository.save(member);
//
//        MemberUpdateDto.UpdateReq updateReq = new MemberUpdateDto.UpdateReq();
//        updateReq.setPassword("newPassword123");
//        updateReq.setPasswordCheck("newPassword123");
//        updateReq.setName("개명성공");
//        updateReq.setNickname("닉변성공");
//        updateReq.setPhone("010-2345-6789");
//
//        memberRepository.findById(member.getId()).orElseThrow();
//        assertEquals("개명성공", member.getName());
//        assertEquals("닉변성공", member.getNickname());
//        assertEquals("010-2345-6789", member.getPhone());
//    }
//
//    @Test
//    @DisplayName("회원 탈퇴")
//    void resignMember() {
//        Member member = new Member();
//        member.setUserId("resign123");
//        member.setPassword("password123");
//        member.setPhone("010-3333-3333");
//        member.setNickname("닉넴임");
//        member.setName("이름임");
//        memberRepository.save(member);
//
//        MemberResignDto.ResignReq resignReq = new MemberResignDto.ResignReq();
//        resignReq.setUserId("resign123");
//        resignReq.setPassword("password123");
//        resignReq.setPhone("010-3333-3333");
//        try {
//            memberService.memberResign(member.getId(), resignReq);
//        } catch (BizException e) {
//            fail(e.getMessage());
//        }
//
//        Member resignedMember = memberRepository.findById(member.getId()).orElseThrow();
//        assertEquals(ResignYn.Y, resignedMember.getResignYn());
//    }
//
//    @Test
//    @DisplayName("아이디 찾기")
//    void findUserId() {
//        Member member = new Member();
//        member.setUserId("find123");
//        member.setPassword("password123");
//        member.setName("namee");
//        member.setNickname("nicknn");
//        member.setPhone("010-7777-7777");
//        memberRepository.save(member);
//
//        FindDto.UserIdReq userIdReq = new FindDto.UserIdReq();
//        userIdReq.setName("namee");
//        userIdReq.setPhone("010-7777-7777");
//
//        String userId = null;
//        try {
//            userId = memberService.findUserId(userIdReq);
//        } catch (BizException e) {
//            fail(e.getMessage());
//        }
//
//        assertEquals("find123", userId);
//    }
}