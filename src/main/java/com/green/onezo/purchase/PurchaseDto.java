package com.green.onezo.purchase;


import com.green.onezo.member.Member;
import com.green.onezo.pay.Pay;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
    private Long id;

    private Long memberId;
    @NotBlank(message = "로그인한 유저의 이름")
    private String memberName;
    @NotBlank(message = "로그인한 유저의 이메일")
    private String memberUserId;
    @NotBlank
    private LocalDateTime payDate;

}
