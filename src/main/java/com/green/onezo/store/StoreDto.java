package com.green.onezo.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.enum_column.TakeInOut;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long id;

    private String storeName;
    private String address;


    @JsonIgnore
    private String addressOld;
    @JsonIgnore
    private String storePhone;
    @JsonIgnore
    private String storeHours;
    @JsonIgnore
    private TakeInOut orderType;

}
