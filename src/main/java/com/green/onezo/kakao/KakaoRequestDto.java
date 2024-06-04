package com.green.onezo.kakao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoRequestDto {
    @Schema(example = "jjj200820@gmail.com")
    private String email;
    @Schema(example = "wjdwls")
    private String nickname;

}
