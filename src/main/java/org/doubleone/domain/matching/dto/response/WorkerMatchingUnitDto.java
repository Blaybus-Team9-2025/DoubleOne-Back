package org.doubleone.domain.matching.dto.response;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import org.doubleone.domain.condition.entity.WorkType;
import org.doubleone.domain.endMatching.entity.EndMatching;

@Builder
public record WorkerMatchingUnitDto(
    LocalDate startDate,
    LocalDate endDate,
    String seniorName,
    WorkType workType,
    long workPeriod
) {

  public static WorkerMatchingUnitDto from(EndMatching endMatching){
    return WorkerMatchingUnitDto.builder()
        .startDate(endMatching.getStartDate())
        .endDate(endMatching.getEndDate())
        .seniorName(endMatching.getMatching().getCondition().getSenior().getName())
        .workType(endMatching.getMatching().getCondition().getWorkType())
        .workPeriod( (endMatching.getEndDate() == null) ? ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now()) : ChronoUnit.DAYS.between(endMatching.getStartDate(), endMatching.getEndDate()))
        .build();
  }

}
