package com.green.onezo.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailDto {

    @JsonIgnore
    private Long cartDetailId;

    private Long menuId;

    private int quentity;

    @JsonIgnore
    private Long cartItemId;


}
