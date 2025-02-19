package org.doubleone.domain.matching.dto.response;

import lombok.Builder;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.member.entity.MemberType;

import java.util.Map;

@Builder
public record ManagerMatchingStatResponseDto(
    String name,
    MemberType memberType,
    String profileImg,
    int totalMatchCount,          // 전체 매칭 횟수
    int newMatchCount,            // 신규 매칭 횟수
    Map<String, Integer> monthlyMatchCounts, // 달별 매칭 횟수
    int progressMatchCount,      // 진행 중 매칭 횟수
    int beforeMatchCount,        // 대기 중 매칭 횟수
    int endMatchCount,           // 완료된 매칭 횟수
    int waitingMatchCount,       // 응답 대기 매칭 횟수
    int pendingMatchCount,       // 조율 요청 매칭 횟수
    int acceptedMatchCount,      // 수락된 매칭 횟수
    int rejectedMatchCount,      // 거절된 매칭 횟수
    double avgMatchesPerSenior, // 노인별 매칭 건수 비율
    double acceptanceRate,       // 수락 비율
    double rejectionRate         // 거절 비율
) {

  public static ManagerMatchingStatResponseDto from(Manager manager,
      int totalMatchCount,
      int newMatchCount,
      Map<String, Integer> monthlyMatchCounts,
      int progressMatchCount,
      int beforeMatchCount,
      int endMatchCount,
      int waitingMatchCount,
      int pendingMatchCount,
      int acceptedMatchCount,
      int rejectedMatchCount,
      double avgMatchesPerSenior,
      double acceptanceRate,
      double rejectionRate) {
    return ManagerMatchingStatResponseDto.builder()
        .name(manager.getName())
        .memberType(MemberType.MANAGER)
        .profileImg(manager.getProfileImg())
        .totalMatchCount(totalMatchCount)
        .newMatchCount(newMatchCount)
        .monthlyMatchCounts(monthlyMatchCounts)
        .progressMatchCount(progressMatchCount)
        .beforeMatchCount(beforeMatchCount)
        .endMatchCount(endMatchCount)
        .waitingMatchCount(waitingMatchCount)
        .pendingMatchCount(pendingMatchCount)
        .acceptedMatchCount(acceptedMatchCount)
        .rejectedMatchCount(rejectedMatchCount)
        .avgMatchesPerSenior(avgMatchesPerSenior)
        .acceptanceRate(acceptanceRate)
        .rejectionRate(rejectionRate)
        .build();
  }

}
