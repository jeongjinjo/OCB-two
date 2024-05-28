package com.green.onezo.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    //카카오 로그인
    @GetMapping("oauth/kakao/callback")
            public String kakaoCallback(String code){
        System.out.println("code : "+code);

//    public @ResponseBody String kakaoCallback(String code) {
        kakaoService.getKakaoToken(code);

//        String redirectTo="http://localhost:3000";
//
//        return "redirect:"+redirectTo;
        return "카카오 로그인 성공";

    }

}
