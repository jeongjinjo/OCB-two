package com.green.onezo.purchase;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.enum_column.TakeInOut;
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
    @JsonIgnore
    private Long id;

    @NotBlank
    private LocalDateTime payDate;

    private int totalPrice;

    private String storeName;

    private TakeInOut takeInOut;

}
