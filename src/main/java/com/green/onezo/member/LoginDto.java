package com.green.onezo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

public class LoginDto {
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class logReq {
        @Schema(description = "사용자 아이디")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영어 대소문자와 숫자로만 이루어져 있습니다.")
        @Size(min = 5, max = 20, message = "아이디는 5글자에서 20글자 사이로 입력해주세요.")
        @NotBlank(message = "아이디는 필수 입력 사항입니다.")
        private String userId;

        @NotBlank(message = "패스워드는 필수 입력 사항입니다.")
        @Size(min = 4, max = 20, message = "패스워드는 최소 4자 이상 20자 이하로 입력해야합니다.")
        private String password;

    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class logRes {
        private String message;
    }

}
