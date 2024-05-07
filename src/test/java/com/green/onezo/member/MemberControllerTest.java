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

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 정보 업데이트")
    void updateMember() {
        Member newMember = new Member();
        newMember.setUserId("test123@naver.com");
        newMember.setPassword("password123");
        newMember.setPhone("010-1234-5678");
        newMember.setNickname("nickname");
        newMember.setName("test");
        memberRepository.save(newMember);

        MemberUpdateDto.UpdateReq updateReq = new MemberUpdateDto.UpdateReq();
        updateReq.setPassword("newPassword123");
        updateReq.setPasswordCheck("newPassword123");
        updateReq.setName("개명성공");
        updateReq.setNickname("닉변성공");
        updateReq.setPhone("010-2345-6789");
        try {
            memberService.memberUpdate(newMember.getId(), updateReq);
        } catch (BizException e) {
            fail(e.getMessage());
        }

        Member updatedMember = memberRepository.findById(newMember.getId()).orElseThrow();
        assertEquals("개명성공", updatedMember.getName());
        assertEquals("닉변성공", updatedMember.getNickname());
        assertEquals("010-2345-6789", updatedMember.getPhone());
    }
    @Test
    @DisplayName("회원 탈퇴")
    void resignMember() {
        Member newMember = new Member();
        newMember.setUserId("testRS123@naver.com");
        newMember.setPassword("password123");
        newMember.setPhone("010-3333-3333");
        newMember.setNickname("닉넴임");
        newMember.setName("이름임");
        memberRepository.save(newMember);

        MemberResignDto.ResignReq resignReq = new MemberResignDto.ResignReq();
        resignReq.setUserId("testRS123@naver.com");
        resignReq.setPassword("password123");
        resignReq.setPhone("010-3333-3333");
        try {
            memberService.memberResign(newMember.getId(), resignReq);
        } catch (BizException e) {
            fail(e.getMessage());
        }

        Member resignedMember = memberRepository.findById(newMember.getId()).orElseThrow();
        assertEquals(ResignYn.Y, resignedMember.getResignYn());
    }
}