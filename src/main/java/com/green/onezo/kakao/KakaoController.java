package com.green.onezo.kakao;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/kakao")
//@Operation(summary = )
public class KakaoController {

//    @GetMapping("oauth/kakao/callback")
//            public String kakaoCallback(String code){
//        System.out.println("code : "+code);
//        kakaoService.getKakaoToken(code);
//        return "카카오 로그인 성공";
//    }

    @PostMapping("login")
    public String kakaoLogin(
            @RequestBody KakaoLoginDto kakaoLoginDto) throws Exception{
        System.out.println("#########"+ kakaoLoginDto);

        // 1. 회원가입
        // 2. accessToken, refreshTokren 발급해줘야되

        return "member/testPage";
    }

}

