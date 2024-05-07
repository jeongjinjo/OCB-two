package com.green.onezo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

public class MemberUpdateDto {

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRes {
        private String message;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReq {

        @Size(min = 4, max = 20, message = "패스워드는 최소 4자 이상 20자 이하로 입력해야합니다.")
        private String password;

        @Size(min = 4, max = 20, message = "패스워드를 한번 더 입력해 주세요.")
        private String passwordCheck;

        @Size(max = 10, message = "이름은 10자까지 입력이 가능합니다.")
        private String name;

        @Size(min = 2, max = 10, message = "닉네임의 크기는 2에서 10 사이여야 합니다.")
        private String nickname;

        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 양식에 맞지 않습니다. XXX-XXXX-XXXX 형식으로 입력해주세요.")
        private String phone;

    }

}
