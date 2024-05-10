package com.green.onezo.cart;

import com.green.onezo.enum_column.TakeInOut;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


public class CartItemDto {

//    @Getter
//    @Setter
//    @ToString
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class CartItemReq {
//
//        @NotNull(message = "멤버 아이디는 필수입니다.")
//        private Long memberId;
//
//        @NotNull(message = "스토어 아이디는 필수입니다.")
//        private Long storeId;
//
////        @NotNull(message = "포장 여부는 필수입니다.")
////        private TakeInOut takeInOut;
//
//        //@NotNull(message = "메뉴 아이디는 필수입니다.")
//        private Long menuId; // 업뎃
//
//        //@NotNull(message = "수량은 필수입니다.")
//        @Min(value = 1, message = "수량은 최소 1 이상이어야 합니다.")
//        private int quantity; // 업뎃
//
//    }
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

        private String menuName;

        private int quantity;

        private int price;

    }

}
