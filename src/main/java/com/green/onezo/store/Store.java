package com.green.onezo.store;

import com.green.onezo.enum_column.TakeInOut;
import com.green.onezo.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressOld;

    @Column(nullable = false)
    private String storePhone;

    @Column(nullable = false)
    private String storeHours;

    @Enumerated(EnumType.STRING)
    private TakeInOut orderType;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

}
