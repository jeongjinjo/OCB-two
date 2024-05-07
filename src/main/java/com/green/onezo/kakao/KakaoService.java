//package com.green.onezo.kakao;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KakaoService {
//    private final KakaoTokenClient kakaoTokenClient;
//    private final KakaoInfoClient kakaoInfoClient;
//    private String contentType = "Content-Type: application/x-www-form-urlencoded";
//    @Value("ee6034fee6eaca2e42ce04b136729cf7")
//    private String clientId;
//    @Value("i25c2321Aa5A9Jy1r6AgrOj2SihPO0Jn")
//    private String clientSecret;
//    @Value("http://loaclhost:8080/oauth/kakao/callback")
//    private String redirectUrl;
//
//    public String getKakaoToken(String code){
//        System.out.println("getKakaoCode"+code);
//        System.out.println("clientId:"+clientId);
//        System.out.println("clientSecret:"+clientSecret);
//        System.out.println("redirectUrl:"+redirectUrl);
//        System.out.println("");
//        KakaoTokenDto.Request kakaoTokenDtoRequest=
//                KakaoTokenDto.Request.builder()
//                        .grant_type("authorization_code")
//                        .client_id(clientId)
//                        .client_secret(clientSecret)
//                        .redirect_url(redirectUrl)
//                        .code(code)
//                        .build();
//
//        KakaoTokenDto.Response resDto=kakaoTokenClient.getKakaoToken(contentType,kakaoTokenDtoRequest);
//        System.out.println(resDto.getAccess_token());
//
//        String result=kakaoInfoClient.getKakaoInfo("Bearer:"+resDto.getAccess_token());
//        System.out.println(result);
//
//        return "getKakaoToken";
//    }
//}
