package com.green.onezo.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoInfoClient kakaoInfoClient;
    // 1. contentType
    private String contentType = "Content-Type: application/x-www-form-urlencoded";

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.client.redirect}")
    private String redirectUri;

    public String getKakaoToken(String code) {

        System.out.println("getKakaoCode : " + code);
        System.out.println("clientId : " + clientId);
        System.out.println("clientSecret : " + clientSecret);
//        System.out.println("redirectUrl : " + redirectUrl);
        KakaoTokenDto.Request kakaoTokenDtoRequest =
                KakaoTokenDto.Request.builder()
                        .grant_type("authorization_code")
                        .client_id(clientId)
                        .client_secret(clientSecret)
                        .redirect_uri(redirectUri)
                        .code(code)
                        .build();

        KakaoTokenDto.Response resDto = kakaoTokenClient.getKakaoToken(contentType, kakaoTokenDtoRequest);
        System.out.println(resDto);

//        KakaoTokenDto.Response resDto=kakaoTokenClient.getKakaoToken(contentType,kakaoTokenDtoRequest);
        System.out.println(resDto.getAccess_token());
//
        String result=kakaoInfoClient.getKakaoInfo("Bearer "+resDto.getAccess_token());
        System.out.println(result);

        return "getKakaoToken";
    }
}
