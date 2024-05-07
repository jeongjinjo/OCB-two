package com.green.onezo.jwt;

import com.green.onezo.enum_column.Role;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDto {

    private long memberId;

    private String accessToken;
    private String refeshToken;

}
