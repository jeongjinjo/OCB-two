package com.green.onezo.jwt;

import lombok.Data;

@Data
public class TokenDTO {
    private String userId;
    private String password;
    private String nickname;
    private String phone;
}
