package com.green.onezo.schedule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@Tag(name = "schedule-controller", description = "매장")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation(summary = "store_id 검색을 통해 매장 일정 조회")
    @GetMapping("/{storeId}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByStoreId(@Parameter (description="매장 ID",required = true)
            @PathVariable Long storeId){
        List<ScheduleDto> schedules = scheduleService.getSchedulesByStoreId(storeId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
}
