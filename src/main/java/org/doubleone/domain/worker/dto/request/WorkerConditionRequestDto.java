package org.doubleone.domain.worker.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.doubleone.domain.worker.dto.response.WorkerLicenseDto;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.schedule.dto.ScheduleDto;
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
    @NotNull
    boolean hasTrained,
    @NotNull
    boolean hasVehicle,
    Map<String, List<String>> services,
    List<WorkPeriod> workPeriods,
    List<WorkerLicenseDto> licenseDtoList,
    List<ScheduleDto> scheduleDtoList,
    List<WorkerRegionDto> regionDtoList
) {

  public WorkerCondition toEntity(Worker worker){
    return WorkerCondition.builder()
        .worker(worker)
        .wageType(wageType)
        .wage(wage)
        .introduce(introduce)
        .hasTrained(hasTrained)
        .hasVehicle(hasVehicle)
            .workPeriods(workPeriods)
            .services(services)
        .build();
  }
}
