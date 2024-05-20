package com.green.onezo.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.enum_column.TakeInOut;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class CartDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cart {

        @NotNull(message = "포장여부를 선택하세요")
        private TakeInOut takeInOut;

        private Long storeId;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CartDetail {

        @JsonIgnore
        private Long cartDetailId;

        private Long menuId;

        private int quentity;

        @JsonIgnore
        private Long cartId;

    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartRes {

        private String storeName;

        private String address;

        private TakeInOut takeInOut;

    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartDetailRes {

        private Long cartDetailId;

        private String menuName;

        private int quantity;

        private int price;

        private String menuImage;

    }

}
