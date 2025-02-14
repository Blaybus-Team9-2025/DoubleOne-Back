package org.doubleone.domain.worker.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.worker.dto.response.WorkerScheduleDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WageType;
import org.doubleone.domain.workerCondition.entity.WorkPeriod;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;

public record WorkerConditionRequestDto(
    @NotNull
    WageType wageType,
    @NotNull
    int wage,
    String introduce,
    List<WorkPeriod> workPeriods,
    List<WorkerScheduleDto> scheduleDtoList,
    List<WorkerRegionDto> regionDtoList
) {

  public WorkerCondition toEntity(Worker worker){
    return WorkerCondition.builder()
        .worker(worker)
        .wageType(wageType)
        .wage(wage)
        .introduce(introduce)
        .workPeriods(workPeriods)
        .build();
  }
}
