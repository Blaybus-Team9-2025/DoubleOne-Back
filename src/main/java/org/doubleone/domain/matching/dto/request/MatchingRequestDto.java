package org.doubleone.domain.matching.dto.request;

import jakarta.validation.constraints.NotNull;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.entity.RunningStatus;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;

public record MatchingRequestDto(
    @NotNull
    Long conditionId,
    @NotNull
    Long workConditionId
) {

  public Matching toEntity(WorkerCondition workerCondition, Condition condition){
    return Matching.builder()
        .workerCondition(workerCondition)
        .condition(condition)
        .matchingStatus(MatchingStatus.BEFORE_REQUEST)
        .runningStatus(RunningStatus.BEFORE_REQUEST)
        .build();
  }
}
