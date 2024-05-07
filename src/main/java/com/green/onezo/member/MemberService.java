package com.green.onezo.member;

import com.green.onezo.enum_column.ResignYn;
import com.green.onezo.global.error.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.onezo.enum_column.Role;
import jakarta.transaction.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signup(SignDto.signReq signDtoReq) {

        //Optional<Member> idmember = memberRepository.findByUserId(signDtoReq.getUserId());
        Optional<Member> idMember = memberRepository.findByUserIdAndResignYn(signDtoReq.getUserId(), ResignYn.N);
        if (idMember.isPresent()) {
            throw new BizException("아이디 중복입니다.");
        }
        Optional<Member> nickMember = memberRepository.findByNicknameAndResignYn(signDtoReq.getNickname(), ResignYn.N);
        if (nickMember.isPresent()) {
            throw new BizException("닉네임 중복입니다.");
        }
        Optional<Member> pMember = memberRepository.findByPhoneAndResignYn(signDtoReq.getPhone(), ResignYn.N);
        if (pMember.isPresent()) {
            throw new BizException("핸드폰 번호 중복입니다.");
        }

        Member member = new Member();
        member.setUserId(signDtoReq.getUserId());
        member.setPassword(passwordEncoder.encode(signDtoReq.getPassword()));//이거 암호화 진행 시켜야됨 jasypt
        member.setNickname(signDtoReq.getNickname());
        member.setName(signDtoReq.getName());
        member.setPhone(signDtoReq.getPhone());
        member.setResignYn(ResignYn.N);
        //유저 권한을 부여.
        member.setRole(Role.USER);

        return memberRepository.save(member);
    }


    //    public Optional<Member> authenticate(String userId,String password) {
//        return memberRepository.findByUserIdAndPassword(userId,password);
//    }

    public boolean authenticate(String userId, String password) {
//        Member member = memberRepository.findByUserId(userId).orElseThrow(
//                () -> new NoSuchElementException("회원을 찾을 수 없습니다. ID: " + userId));
//        return member != null && passwordEncoder.matches(password, member.getPassword());

        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다. ID: " + userId));

        if (member.getResignYn() == ResignYn.Y) {
            throw new IllegalArgumentException("탈퇴한 회원입니다.");
        }

        return passwordEncoder.matches(password, member.getPassword());
    }

 

    // 회원 아이디로 pk 찾기
    public Optional<Long> findMemberId(String userId) {
        Optional<Member> member = memberRepository.findByUserId(userId);
        return member.map(Member::getId);
    }


    // 회원정보수정
    @Transactional
    public void memberUpdate(Long memberId, MemberUpdateDto.UpdateReq updateDtoReq) {
        Member member = memberRepository.findById(memberId)
                .filter(m -> m.getResignYn().equals(ResignYn.N))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원아이디를 찾을 수 없거나 탈퇴한 회원입니다."));

        if (updateDtoReq.getPassword() != null && !updateDtoReq.getPassword().isEmpty()) {
            if (!updateDtoReq.getPassword().equals(updateDtoReq.getPasswordCheck())) {
                throw new BizException("재확인 비밀번호가 일치하지 않습니다.");
            }
            member.setPassword(passwordEncoder.encode(updateDtoReq.getPassword()));
        }

        if (updateDtoReq.getName() != null && !updateDtoReq.getName().isEmpty()) {
            member.setName(updateDtoReq.getName());
        }
        if (updateDtoReq.getNickname() != null && !updateDtoReq.getNickname().isEmpty() && !updateDtoReq.getNickname().equals(member.getNickname())) {
            if (memberRepository.findByNicknameAndResignYn(updateDtoReq.getNickname(), ResignYn.N).isPresent()) {
                throw new BizException("닉네임 중복입니다.");
            }
            member.setNickname(updateDtoReq.getNickname());
        } else {
            member.setNickname(updateDtoReq.getNickname());
        }
        if (updateDtoReq.getPhone() != null && !updateDtoReq.getPhone().isEmpty() && !updateDtoReq.getPhone().equals(member.getPhone())) {
            if (memberRepository.findByPhoneAndResignYn(updateDtoReq.getPhone(), ResignYn.N).isPresent()) {
                throw new BizException("전화번호 중복입니다.");
            }
            member.setPhone(updateDtoReq.getPhone());
        } else {
            member.setPhone(updateDtoReq.getPhone());
        }

        memberRepository.save(member);
    }

    // 회원탈퇴
    public void memberResign(Long memberId, MemberResignDto.ResignReq resignReq) {
        Member member = memberRepository.findById(memberId)
                .filter(m -> m.getResignYn().equals(ResignYn.N))
                .orElseThrow(() -> new BizException("해당하는 회원아이디를 찾을 수 없거나 탈퇴한 회원입니다."));

        if (!member.getUserId().equals(resignReq.getUserId()) ||
                !passwordEncoder.matches(resignReq.getPassword(), member.getPassword()) ||
                !member.getPhone().equals(resignReq.getPhone())) {
            throw new BizException("회원 정보가 잘못되었습니다.");
        }

        member.setResignYn(ResignYn.Y);
        memberRepository.save(member);
    }


    // 아이디 찾기
    public String findUserId(String name, String phone) throws BizException {
        return memberRepository.findByNameAndPhoneAndResignYn(name, phone, ResignYn.N)
                .map(member -> member.getUserId().substring(0, member.getUserId().length() - 3) + "***")
                .orElseThrow(() -> new BizException("해당 이름과 전화번호로 등록된 회원이 없거나 탈퇴하였습니다."));
    }

    // 비밀번호 찾기
    public String findPassword(String userId, String name, String phone) throws BizException {
        return memberRepository.findByUserIdAndNameAndPhoneAndResignYn(userId, name, phone, ResignYn.N)
                .map(Member::getPassword)
                .orElseThrow(() -> new BizException("해당 아이디, 이름 및 전화번호로 등록된 회원이 없습니다."));
    }

    // 회원 정보 조회
    public FindDto.InfoRes getMemberInfo(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findByIdAndResignYn(memberId, ResignYn.N);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return FindDto.InfoRes.builder()
                    .userId(member.getUserId())
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .phone(member.getPhone())
                    .build();
        } else {
            throw new BizException("해당하는 멤버를 찾을 수 없습니다.");
        }
    }


    // 영속성 컨텍스트에 있는 내용이 디비와 동기화 되기 때문에 setMethod를 사용하면 update 구문이 날아감
    @Transactional
    public boolean updatePassword(String userId, String name, String phone) {
        Optional<Member> dbMember = memberRepository.findByUserIdAndNameAndPhone(userId, name, phone);
        if(dbMember.isEmpty()){
            return false;
        }else{
            dbMember.get().setPassword(passwordEncoder.encode("임시1234"));
            return true;
        }
    }
}

