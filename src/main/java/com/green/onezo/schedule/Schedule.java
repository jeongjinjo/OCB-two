package com.green.onezo.schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long schedule_id;

    @Column(name = "store_id")
    private Long storeId;

    private LocalDate start_date;
    private LocalDate end_date;
    private String content;
    @Enumerated(EnumType.STRING)
    private com.green.onezo.store.delete_yn delete_yn;

}
