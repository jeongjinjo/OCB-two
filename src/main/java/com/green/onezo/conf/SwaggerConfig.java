package com.green.onezo.conf;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "원조치킨 프랜차이즈 웹 주문 프로젝트",
                description = "이 API 문서는 원조치킨 프랜차이즈 웹 주문 시스템을 위해 설계되었습니다. 회원 관리, 메뉴 조회, 매장 정보, 장바구니 기능, 결제 처리 및 주문 내역 조회 기능을 제공합니다.",
                version = "v1.0.0"
        )
        //,servers = @Server(url = "http://api.example.com", description = "Production server")
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        String[] paths = {"/**"}; //

        return GroupedOpenApi.builder()
                .group("모든 Controller")
                .pathsToMatch(paths)
                .build();
    }


        @Bean
        public OpenAPI openAPI() {
            // SecurityScheme 설정
            String jwtSchemeName = "accessToken";
            SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
            Components components = new Components()
                    .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                            .name(jwtSchemeName)
                            .type(SecurityScheme.Type.HTTP) // HTTP 방식
                            .scheme("bearer")
                            .bearerFormat("JWT"));
            return new OpenAPI()
                    .components(components)
                    .addSecurityItem(securityRequirement);
        }


    }