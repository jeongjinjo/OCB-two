package com.green.onezo.schedule;

import com.green.onezo.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


    List<ScheduleDto> findByStoreId(Long storeId);

}