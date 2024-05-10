package com.green.onezo.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.member.Member;
import com.green.onezo.menu.Menu;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailDto {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long menuId;

    private String menuMenuName;

    private int menuPrice;

    private String menuCategory;

    private String memberPurchaseName;

    private String storeMenuStoreName;

    private String storeMenuStoreAddress;










}
