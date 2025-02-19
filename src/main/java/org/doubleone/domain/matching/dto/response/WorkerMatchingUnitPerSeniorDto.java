package org.doubleone.domain.matching.dto.response;

import java.util.List;
import lombok.Builder;
import org.doubleone.domain.workerCondition.entity.WorkPeriod;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;

@Builder
public record WorkerMatchingUnitPerSeniorDto(
    Long workerConditionId,
    String name,
    String address,
    String profileImg,
    List<WorkPeriod> workPeriods
) {

  public static WorkerMatchingUnitPerSeniorDto from(WorkerCondition workerCondition){
    return WorkerMatchingUnitPerSeniorDto.builder()
        .workerConditionId(workerCondition.getWorkerConditionId())
        .name(workerCondition.getWorker().getName())
        .address(workerCondition.getWorker().getAddress())
        .profileImg(workerCondition.getWorker().getProfileImg())
        .workPeriods(workerCondition.getWorkPeriods())
        .build();
  }
}
