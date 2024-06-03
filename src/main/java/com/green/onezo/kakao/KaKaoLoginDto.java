package com.green.onezo.kakao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class KakaoLoginDto {
    @Schema(example = "aaaa@naver.com")
    String email;
    @Schema(example = "nickname")
    String nickname;
}
