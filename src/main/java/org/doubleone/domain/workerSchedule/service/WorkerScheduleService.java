package org.doubleone.domain.workerSchedule.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.schedule.repository.ScheduleRepository;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.doubleone.domain.workerSchedule.repository.WorkerScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerScheduleService {
  private final WorkerScheduleRepository workerScheduleRepository;
  private final ScheduleRepository scheduleRepository;

  public void createWorkerSchedule(WorkerCondition workerCondition, ScheduleDto scheduleDto) {
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
    WorkerSchedule workerSchedule = scheduleDto.toEntity(workerCondition, schedule);
    workerScheduleRepository.save(workerSchedule);
  }

  public void update(WorkerCondition workerCondition, ScheduleDto scheduleDto) {
    // scheduleDto에 해당하는 schedule 존재하는지 조회
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

    Optional<WorkerSchedule> existingWorkerSchedule = workerScheduleRepository.findByWorkerConditionAndSchedule(
        workerCondition, schedule
    );

    if (existingWorkerSchedule.isPresent()) {
      WorkerSchedule workerSchedule = existingWorkerSchedule.get();
      workerSchedule.updateSchedule(schedule);
    } else {
      WorkerSchedule newWorkerSchedule = WorkerSchedule.builder()
          .workerCondition(workerCondition)
          .schedule(schedule)
          .build();
      workerScheduleRepository.save(newWorkerSchedule);
    }
  }

}
