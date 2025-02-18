package org.doubleone.domain.schedule.dto;

import lombok.Builder;
import org.doubleone.domain.endMatching.entity.EndMatching;
import org.doubleone.domain.schedule.entity.Day;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.senior.entity.SeniorSchedule;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

@Builder
public record ScheduleDto(
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

  public static ScheduleDto from(WorkerSchedule workerSchedule){
    return ScheduleDto.builder()
        .day(workerSchedule.getSchedule().getDay())
        .startTime(workerSchedule.getSchedule().getStartTime())
        .endTime(workerSchedule.getSchedule().getEndTime())
        .build();
  }

  public static ScheduleDto from(EndMatching endMatching){
    return ScheduleDto.builder()
        .day(endMatching.getSchedule().getDay())
        .startTime(endMatching.getSchedule().getStartTime())
        .endTime(endMatching.getSchedule().getEndTime())
        .build();
  }

  public static ScheduleDto from(SeniorSchedule seniorSchedule){
    return ScheduleDto.builder()
        .day(seniorSchedule.getSchedule().getDay())
        .startTime(seniorSchedule.getSchedule().getStartTime())
        .endTime(seniorSchedule.getSchedule().getEndTime())
        .build();
  }
}
