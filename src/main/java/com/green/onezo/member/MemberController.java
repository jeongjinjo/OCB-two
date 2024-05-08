package com.green.onezo.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.onezo.global.error.BizException;
import com.green.onezo.jwt.JwtTokenDto;

import com.green.onezo.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final MemberDetailsService memberDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Operation(summary = "회원 가입",
            description = "회원 가입")
    @PostMapping("signUp")
    public ResponseEntity<SignDto.signRes> signup(
            @RequestBody @Valid SignDto.signReq signDtoReq
    ) {
        memberService.signup(signDtoReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(SignDto.signRes.builder()
                .message("회원가입이 완료되었습니다.")
                .build());
    }

    //아이디 중복체크
    @Operation(summary = "아이디 중복체크",
            description = "입력한 아이디를 db와 대조한뒤 중복 체크")
    @PostMapping("checkId")
    public ResponseEntity<String> checkId(@RequestBody @Valid AuthCheckIdDto authCheckIdDto) {
        boolean checkIDDuplicate = memberRepository.existsByUserId(authCheckIdDto.getUserId());

        if (checkIDDuplicate) {
            return ResponseEntity.ok("중복된 아이디입니다");
        } else {
            return ResponseEntity.ok("사용가능한 아이디 입니다");
        }
    }

    //비밀번호 확인
    @Operation(summary = "비밀번호 확인",
            description = "비밀번호 확인과 비밀번호가 일치하는지 확인")
    @PostMapping("passwordCheck")
    public ResponseEntity<String> passwordCheck(@RequestBody @Valid PwDto.pwReq pwDto) {
        String password = pwDto.getPassword();
        String passwordCheck = pwDto.getPasswordCheck();

        if (password.equals(passwordCheck)) {
            return ResponseEntity.ok("비밀번호가 일치합니다.");
        } else {
            return ResponseEntity.ok("비밀번호가 일치하지 않습니다.");
        }
    }


    @Operation(summary = "닉네임 중복체크",
            description = "입력한 닉네임을 db와 대조한뒤 중복 체크")
    @PostMapping("checkNickname")
    public ResponseEntity<CheckNickDto.Res> checkNick(@RequestBody @Valid CheckNickDto.Req checkNickDtoReq) {
        boolean checkNickDuplicate = memberRepository.existsByNickname(checkNickDtoReq.getNickname());

        if (checkNickDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(CheckNickDto.Res.builder()
                    .message("중복된 검사입니다.")
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(CheckNickDto.Res.builder()
                    .message("사용가능한 닉네임입니다.")
                    .build());
        }
    }


    @Operation(summary = "로그인 기능",
            description = "아이디(0505수정: 아이디는 이메일 형식이 아닙니다!) ,비밀번호를 DB와 대조해 회원이라면 로그인")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDto.logReq loginDto) {
        String userId = loginDto.getUserId();
        String password = loginDto.getPassword();

        boolean isAuthenticated = memberService.authenticate(userId, password);
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 비밀번호가 틀렸습니다. 아이디와 비밀번호를 확인하세요");
        } else {
            UserDetails userDetails = memberDetailsService.loadUserByUsername(userId);
            Member member = memberRepository.findByUserId(userId).orElse(new Member());
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            JwtTokenDto jwtTokenDto = new JwtTokenDto(member.getId(), accessToken, refreshToken);
            return ResponseEntity.status(HttpStatus.OK).body(jwtTokenDto);
        }
    }

    // 유저아이디로 pk 찾기
    @GetMapping("/{userId}")
    @Operation(summary = "유저아이디로 pk 찾기",
            description = "userId를 입력하면 해당하는 pk값이 반환됩니다.")
    public ResponseEntity<?> getMemberId(@Parameter(description = "회원아이디", required = true) @PathVariable String userId) {
        return memberService.findMemberId(userId)
                .map(pk -> ResponseEntity.ok("memberId : " + pk ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //카카오 로그인
    @GetMapping("oauth/kakao/callback")
    public String kakaoCallback(String code) {
        System.out.println("code=" + code);
        return code;
    }


    @PutMapping("/update/{memberId}")
    @Operation(summary = "회원 정보 수정",
            description = "로그인 한 회원의 패스워드(패스워드확인), 이름, 닉네임, 전화번호를 변경 할 수 있습니다.")
    public ResponseEntity<String> updateMember(@RequestBody @Valid MemberUpdateDto.UpdateReq updateReq,
                                               @Parameter(description = "멤버 PK", required = true) @PathVariable Long memberId) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            memberService.memberUpdate(memberId, updateReq);
            return ResponseEntity.ok("회원 정보가 성공적으로 업데이트되었습니다.");
        } catch (BizException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 업데이트 실패: " + e.getMessage());
        }
    }

    @PutMapping("/resign/{memberId}")
    @Operation(summary = "회원 탈퇴",
            description = "로그인 한 회원의 아이디, 패스워드, 전화번호를 입력해 회원탈퇴를 합니다.")
    public ResponseEntity<String> resignMember(@RequestBody @Valid MemberResignDto.ResignReq resignReq, @Parameter(description = "멤버 PK", required = true) @PathVariable Long memberId) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            memberService.memberResign(memberId, resignReq);
            return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("잘못된 회원 정보: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 실패: " + e.getMessage());
        }
    }

    @GetMapping("/findId/{name}/{phone}")
    @Operation(summary = "아이디 찾기",
            description = "회원의 이름과 전화번호를 입력하면 끝 3글자를 제외한 회원 아이디가 반환됩니다.")
    public ResponseEntity<FindDto.UserIdRes> findUserId(
            @Parameter(description = "이름", required = true) @PathVariable String name,
            @Parameter(description = "전화번호", required = true) @PathVariable String phone
    ) {
        try {
            String userId = memberService.findUserId(name, phone);
            return ResponseEntity.ok(new FindDto.UserIdRes(userId));
        } catch (BizException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // 비밀번호 찾기 -> 임시 비밀번호 발급
    @GetMapping("/findPw/{userId}/{name}/{phone}")
    @Operation(summary = "임시 비밀번호 발급",
            description = "회원 아이디와 이름, 전화번호를 입력하면 임시 비밀번호가 발행됩니다. 로그인시에 비밀번호 변경하세요 \n" +
                    "임시 비밀번호를 발급하게 됩니다.")
    public ResponseEntity<FindDto.PasswordRes> findPassword(
            @Parameter(description = "아이디", required = true) @PathVariable String userId,
            @Parameter(description = "이름", required = true) @PathVariable String name,
            @Parameter(description = "전화번호", required = true) @PathVariable String phone
    ) {
        try {
            boolean result = memberService.updatePassword(userId, name, phone);
            if(result)
                return ResponseEntity.ok(new FindDto.PasswordRes("임시1234"));
            else
                return ResponseEntity.ok(new FindDto.PasswordRes("아이디 이름 전화번호를 다시 입력하세요"));

        } catch (ExpiredJwtException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 회원 정보 조회
    @GetMapping("/info/{memberId}")
    @Operation(summary = "회원 정보 조회",
            description = "회원 정보 수정 시에 필요한 정보를 반환합니다.")
    public ResponseEntity<FindDto.InfoRes> getMemberInfo(@Parameter(description = "멤버PK", required = true) @PathVariable Long memberId) {
        try {
            FindDto.InfoRes memberInfo = memberService.getMemberInfo(memberId);
            return ResponseEntity.ok(memberInfo);
        } catch (BizException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


