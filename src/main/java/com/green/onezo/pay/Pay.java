package com.green.onezo.pay;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private Long amount;

    @Column
    @Setter
    private String paymentKey;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerName customerName;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private String paySuccessYn;

    @Column
    private String payFailReason;

    @Setter
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public PayRes toDto(){
        return PayRes.builder()
                .payType(payType.name())
                .amount(amount)
                .orderId(orderId)
                .userId(userId)
                .customerName(customerName.name())
                .createDate(LocalDateTime.now())
                .build();
    }


}
