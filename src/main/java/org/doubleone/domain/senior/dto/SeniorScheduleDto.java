package org.doubleone.domain.senior.dto;

import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.schedule.entity.Day;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.senior.entity.SeniorSchedule;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

public record SeniorScheduleDto(
        Day day,
        String startTime,
        String endTime
) {

    public SeniorSchedule toEntity(Condition condition, Schedule schedule){
        return SeniorSchedule.builder()
                .condition(condition)
                .schedule(schedule)
                .build();
    }
}