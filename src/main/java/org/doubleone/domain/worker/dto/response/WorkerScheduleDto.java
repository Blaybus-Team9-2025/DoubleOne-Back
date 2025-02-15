package org.doubleone.domain.worker.dto.response;

import lombok.Builder;
import org.doubleone.domain.schedule.entity.Day;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

@Builder
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

  public static WorkerScheduleDto from(WorkerSchedule workerSchedule){
    return WorkerScheduleDto.builder()
        .day(workerSchedule.getSchedule().getDay())
        .startTime(workerSchedule.getSchedule().getStartTime())
        .endTime(workerSchedule.getSchedule().getEndTime())
        .build();
  }
}
