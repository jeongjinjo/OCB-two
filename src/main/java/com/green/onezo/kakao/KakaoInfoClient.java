//package com.green.onezo.kakao;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.cloud.openfeign.SpringQueryMap;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//@FeignClient(name = "kakaoInfo",url="http://kapi.kakao.com")
//public interface KakaoInfoClient {
//
//    @PostMapping(value = "/v2/user/me",consumes = "application/json")
//    String getKakaoInfo(
//            @RequestHeader("Authorization") String accessToken);
//
//}
