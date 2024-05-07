package com.green.onezo.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class PwDto {
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class pwReq {

        @NotBlank(message = "패스워드는 필수 입력 사항입니다.")
        @Size(min = 4, max = 20, message = "패스워드는 최소 4자 이상 20자 이하로 입력해야합니다.")
        private String password;

        @NotBlank(message = "패스워드 확인은 필수 입력 사항입니다.")
        @Size(min = 4, max = 20, message = "패스워드는 최소 4자 이상 20자 이하로 입력해야합니다.")
        private String passwordCheck;

    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class pwRes {
        private String message;
    }

}
