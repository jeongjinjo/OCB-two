package com.green.onezo.cart;


import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDetail {

    private Long cartItemId;

    private TakeInOut takeInOut;

    private Long memberId;
}
