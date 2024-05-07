package com.green.onezo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

public class SignDto {


    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class signReq {
        @Schema(description = "사용자 아이디")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영어 대소문자나 숫자로만 입력해주세요.")
        @Size(min = 5, max = 20, message = "아이디는 5글자에서 20글자 사이로 입력해주세요.")
        @NotBlank(message = "아이디는 필수 입력 사항입니다.")
        private String userId;

        @NotBlank(message = "패스워드는 필수 입력 사항입니다.")
        @Size(min = 4, max = 20, message = "패스워드는 최소 4자 이상 20자 이하로 입력해야합니다.")
        private String password;

        @NotBlank(message = "패스워드 확인은 필수 입력 사항입니다.")
        @Size(min = 4, max = 20, message = "패스워드는 최소 4자 이상 20자 이하로 입력해야합니다.")
        private String passwordCheck;

        @NotBlank
        @Size(max = 10, message = "이름은 10자까지 입력이 가능합니다.")
        @Schema(description = "사용자 이름")
        private String name;

        @Size(min = 2, max = 10, message = "닉네임의 크기는 2에서 10 사이여야 합니다.")
        @Schema(description = "사용자의 닉네임")
        private String nickname;

        @Schema(description = "사용자의 전화번호", example = "010-0000-0000")
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
    public static class signRes {
        private String message;
    }

}


