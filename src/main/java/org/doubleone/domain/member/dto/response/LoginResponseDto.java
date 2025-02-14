package org.doubleone.domain.member.dto.response;

import lombok.Builder;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.entity.MemberType;

@Builder
public record LoginResponseDto(
    Long memberId,
    MemberType memberType,
    Long workerId,
    Long managerId,
    String accessToken,
    String refreshToken
) {

  public static LoginResponseDto from(String accessToken, String refreshToken, Member member, Long workerId, Long managerId) {
    return LoginResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .memberId(member.getMemberId())
        .memberType(member.getMemberType())
        .workerId(workerId)
        .managerId(managerId)
        .build();
  }
}
