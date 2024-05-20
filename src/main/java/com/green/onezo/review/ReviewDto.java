package com.green.onezo.review;

import com.green.onezo.enum_column.ResignYn;
import com.green.onezo.member.Member;
import com.green.onezo.store.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {


    private String comment;
    private Long storeId;
    private int star;
}
