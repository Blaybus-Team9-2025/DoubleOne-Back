package org.doubleone.domain.matching.dto.response;

import lombok.Builder;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.member.entity.MemberType;

@Builder
public record ManagerMatchingStatResponseDto(
    String name,
    MemberType memberType,
    String profileImg,
    double progressMatchRate,
    double completeMatchRate,
    int acceptedMatchCount,
    int rejectedMatchCount,
    int pendingMatchCount
) {

  public static ManagerMatchingStatResponseDto from(Manager manager, double progressMatchRate, double completeMatchRate, int acceptedMatchCount, int rejectedMatchCount, int pendingMatchCount){
    return ManagerMatchingStatResponseDto.builder()
        .name(manager.getName())
        .memberType(MemberType.MANAGER)
        .profileImg(manager.getProfileImg())
        .progressMatchRate(progressMatchRate)
        .completeMatchRate(completeMatchRate)
        .acceptedMatchCount(acceptedMatchCount)
        .rejectedMatchCount(rejectedMatchCount)
        .pendingMatchCount(pendingMatchCount)
        .build();
  }

}
