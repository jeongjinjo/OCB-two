//package com.green.onezo.purchase;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Calculate {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "calculate_id")
//    private Long id;
//
//    private float charge;
//
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Purchase purchase;
//
//}
