package com.green.onezo.schedule;

import com.green.onezo.store.FavoriteStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<ScheduleDto> getSchedulesByStoreId(Long storeId) {
        return scheduleRepository.findByStoreId(storeId);
    }
}
