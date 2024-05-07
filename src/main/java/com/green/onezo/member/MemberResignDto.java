package com.green.onezo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

public class MemberResignDto {

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResignReq {

        @NotBlank(message = "아이디는 필수 입력 사항입니다.")
        private String userId;

        @NotBlank(message = "패스워드는 필수 입력 사항입니다.")
        private String password;

        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 양식에 맞지 않습니다. XXX-XXXX-XXXX 형식으로 입력해주세요.")
        @NotBlank(message = "전화번호는 필수 입력 사항입니다.")
        private String phone;

    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResignRes {
        private String message;
    }
}
