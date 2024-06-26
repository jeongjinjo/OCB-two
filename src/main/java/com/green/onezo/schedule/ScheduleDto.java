package com.green.onezo.schedule;

import com.green.onezo.store.Store;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private Long scheduleId;
    private Long storeId;
    private LocalDate start_date;
    private LocalDate end_date;
    private String content;
    private com.green.onezo.store.delete_yn delete_yn;
}
