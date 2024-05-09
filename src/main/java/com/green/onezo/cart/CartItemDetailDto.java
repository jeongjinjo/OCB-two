package com.green.onezo.cart;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.enum_column.TakeInOut;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDetailDto {


    @NotNull(message = "포장여부를 선택하세요")
    private TakeInOut takeInOut;
//
//    @JsonIgnore
//    private Long memberId;

    private Long storeId;



}
