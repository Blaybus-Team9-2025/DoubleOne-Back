package org.doubleone.domain.worker.dto.response;

import org.doubleone.domain.region.entity.Region;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

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

}
