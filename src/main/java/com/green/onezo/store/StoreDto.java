package com.green.onezo.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.review.Review;
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
    private String addressOld;
    private String storePhone;
    private String storeHours;
    private Review comment;
    private String nickname;
    @JsonIgnore
    private TakeInOut orderType;

}
