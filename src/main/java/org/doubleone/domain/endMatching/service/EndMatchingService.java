package org.doubleone.domain.endMatching.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.endMatching.entity.EndMatching;
import org.doubleone.domain.endMatching.repository.EndMatchingRepository;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.schedule.repository.ScheduleRepository;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EndMatchingService {
  private final EndMatchingRepository endMatchingRepository;
  private final ScheduleRepository scheduleRepository;

  public void updateSchedule(Long endMatchingId, ScheduleDto scheduleDto) {
    EndMatching endMatching = endMatchingRepository.findById(endMatchingId)
        .orElseThrow(() -> new CustomException(ErrorCode.END_MATCHING_NOT_FOUND));
    Schedule schedule = scheduleRepository.findByDayAndStartTimeAndEndTime(
        scheduleDto.day(), scheduleDto.startTime(), scheduleDto.endTime()
    ).orElseGet(() -> {
      Schedule newSchedule = Schedule.builder()
          .day(scheduleDto.day())
          .startTime(scheduleDto.startTime())
          .endTime(scheduleDto.endTime())
          .build();
      return scheduleRepository.save(newSchedule);
    });
    endMatching.updateSchedule(schedule);
  }

}
