package com.green.onezo.member;

import lombok.*;

public class CheckNickDto {

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req{
        private String nickname;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res{
        private String message;
    }

}
