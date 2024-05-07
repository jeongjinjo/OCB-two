package com.green.onezo.member;

import lombok.*;

public class FindDto {

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoRes {

        private String userId;

        private String name;

        private String nickname;

        private String phone;

    }


    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserIdRes {
        private String userId;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordRes {
        private String password;
    }
}
