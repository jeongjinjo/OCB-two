package com.green.onezo.kakao;

import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository; // 추가: MemberRepository를 가져옵니다.
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        GrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().toString());

        return new User(
                member.getEmail(),
                member.getNickname(),
                Collections.singleton(authority)
        );
    }
}