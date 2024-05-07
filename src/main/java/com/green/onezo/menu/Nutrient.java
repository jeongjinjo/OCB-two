package com.green.onezo.menu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Nutrient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutrient_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private float kcal;

    @Column(nullable = false)
    private float na;

    @Column(nullable = false)
    private float sugar;

    @Column(nullable = false)
    private float fat;

    @Column(nullable = false)
    private float protein;

}
