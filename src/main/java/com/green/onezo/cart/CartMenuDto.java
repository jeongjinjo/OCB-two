package com.green.onezo.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartMenuDto {

    @JsonIgnore
    private Long cartItemId;

    private Long menuId;

    private int quantity;
}
