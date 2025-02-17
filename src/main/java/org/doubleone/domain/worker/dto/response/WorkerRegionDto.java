package org.doubleone.domain.worker.dto.response;

import lombok.Builder;
import org.doubleone.domain.region.entity.Region;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

@Builder
public record WorkerRegionDto(
    String city,
    String district,
    String neighborhood
) {

  public WorkerRegion toEntity(WorkerCondition workerCondition, Region region){
    return WorkerRegion.builder()
        .workerCondition(workerCondition)
        .region(region)
        .build();
  }

  public static WorkerRegionDto from(WorkerRegion workerRegion){
    return WorkerRegionDto.builder()
        .city(workerRegion.getRegion().getCity())
        .district(workerRegion.getRegion().getDistrict())
        .neighborhood(workerRegion.getRegion().getNeighborhood())
        .build();
  }

}
