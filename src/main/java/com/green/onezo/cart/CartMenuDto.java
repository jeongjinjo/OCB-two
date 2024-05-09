package com.green.onezo.cart;

import com.green.onezo.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartMenuDto {

    private Long menuId;

    private int quantity;
}
