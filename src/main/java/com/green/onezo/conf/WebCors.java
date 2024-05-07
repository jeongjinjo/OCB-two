package com.green.onezo.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebCors implements WebMvcConfigurer {

//    private final JWTInterceptor jwtInterceptor;

    // InterceptorRegistry에 인터셉터 추가
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/api/cart/**","/api/purchase/**","/v1/**","/auth/update/**","/auth/resign/**") //인터셉터 적용 JWT토큰검사
//                .excludePathPatterns("/token", // 토큰 발급 경로는 인터셉터 적용 제외
//                        "/auth/signUp", // 회원가입 경로는 인터셉터 적용 제외
//                        "/auth/findId", // 아이디찾기
//                        "/auth/findPw", // 비밀번호찾기
//
//                        "/swagger-ui/**", // Swagger UI 관련 경로는 인터셉터 적용 제외
//                        "/swagger-resources/**",
//                        "/swagger-resources/**/**",
//                        "/v2/api-docs",
//                        "/v3/api-docs",
//                        "/h2-console/**",
//                        "/main/**",
//                        "/error",
//                        "/auth/checkNickname",
//                        "index.html",
//                        ""); // 기타 경로는 인터셉터 적용 제외
//    }

//     CORS 설정을 추가
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 설정 적용
                .allowedOrigins("*") // 모든 오리진(Origin)에서 요청 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .allowedMethods( // 모든 HTTP 메서드 허용
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name()
                )
                .maxAge(3600); // 프리플라이트 요청의 유효 시간 설정
    }
}
