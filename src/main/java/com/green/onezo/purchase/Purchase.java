package com.green.onezo.purchase;

import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.member.Member;
import com.green.onezo.store.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;


    @Column(nullable = false)
    private LocalDateTime payDate;// 구매한 일자

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private TakeInOut takeInOut;


}
