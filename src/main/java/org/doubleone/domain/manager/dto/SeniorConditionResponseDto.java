package org.doubleone.domain.manager.dto;

import lombok.Builder;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.entity.RunningStatus;

@Builder
public record SeniorConditionResponseDto(
    Long seniorId,
    String profileImg,
    boolean isEndMatch,
    Long seniorConditionId
) {

  public static SeniorConditionResponseDto from(Condition condition, boolean isEndMatch){
    return SeniorConditionResponseDto.builder()
        .seniorId(condition.getSenior().getSeniorId())
        .profileImg(condition.getSenior().getProfileImg())
        .isEndMatch(isEndMatch)
        .seniorConditionId(condition.getConditionId())
        .build();
  }

}
