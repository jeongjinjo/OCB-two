package com.green.onezo.kakao;

import com.green.onezo.enum_column.Role;
import com.green.onezo.jwt.JwtTokenDto;
import com.green.onezo.jwt.JwtUtil;
import com.green.onezo.member.Member;
import com.green.onezo.member.MemberDetailsService;
import com.green.onezo.member.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoDetailService kakaoDetailService;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Operation(summary = "로그인 기능",
            description = "DB에 이메일과 닉네임을 대조해 회원이라면 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> kakaologin(@RequestBody @Valid KakaoRequestDto kakaoRequestDto) {
        String nickname = kakaoRequestDto.getNickname();
        String email = kakaoRequestDto.getEmail();

        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            member = memberRepository.save(
                        Member.builder()
                            .userId(email)
                            .email(email)
                            .nickname(nickname)
                            .phone("010-0000-0000")
                            .role(Role.USER)
                            .build()
                        );
        }else {
            // 이미 존재하는 회원의 경우 로그인 처리를 진행
            UserDetails userDetails = kakaoDetailService.loadUserByUsername(email);
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            JwtTokenDto jwtTokenDto = new JwtTokenDto(member.getId(), accessToken, refreshToken);
            return ResponseEntity.status(HttpStatus.OK).body(jwtTokenDto);
        }
        //신규 회원인 경우는 가입을 진행
        UserDetails userDetails = kakaoDetailService.loadUserByUsername(email);
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        JwtTokenDto jwtTokenDto = new JwtTokenDto(member.getId(), accessToken, refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(jwtTokenDto);
    }
}