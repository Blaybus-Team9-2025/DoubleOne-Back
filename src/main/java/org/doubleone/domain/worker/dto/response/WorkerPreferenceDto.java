package org.doubleone.domain.worker.dto.response;

import lombok.Builder;
import org.doubleone.domain.schedule.entity.Day;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;

@Builder
public record WorkerPreferenceDto ( // 희망 근무 조건 dto
    String city,
    String district,
    String neighborhood,
    Day day,
    String startTime,
    String endTime,
    int wage

) {
    public static WorkerPreferenceDto from(
        WorkerRegionDto workerRegionDto, WorkerScheduleDto workerScheduleDto, WorkerCondition workerCondition
    ) {
        return WorkerPreferenceDto.builder()
            .city(workerRegionDto.city())
            .district(workerRegionDto.district())
            .neighborhood(workerRegionDto.neighborhood())
            .day(workerScheduleDto.day())
            .startTime(workerScheduleDto.startTime())
            .endTime(workerScheduleDto.endTime())
            .wage(workerCondition.getWage())
            .build();
    }
}
