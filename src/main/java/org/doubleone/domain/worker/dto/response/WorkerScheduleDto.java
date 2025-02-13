package org.doubleone.domain.worker.dto.response;

import org.doubleone.domain.schedule.entity.Day;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

public record WorkerScheduleDto(
    Day day,
    String startTime,
    String endTime
) {

  public WorkerSchedule toEntity(WorkerCondition workerCondition, Schedule schedule){
    return WorkerSchedule.builder()
        .workerCondition(workerCondition)
        .schedule(schedule)
        .build();
  }
}
