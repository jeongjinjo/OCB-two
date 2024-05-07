package com.green.onezo.member;

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
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        Member member = optionalMember.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));


        GrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().toString());

        return new User(
                member.getUserId(),
                member.getPassword(),
                Collections.singleton(authority)
        );
    }
}

