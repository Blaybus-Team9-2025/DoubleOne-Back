package org.doubleone.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.member.entity.Member;

@Getter
public class AuthResponseDto {

  @Schema(description = "회원 아이디 pk")
  private Long memberId;
  @Schema(description = "닉네임")
  private String nickname;
  @Schema(description = "프로필 사진 url")
  private String profileImage;
  @Schema(description = "레벨")
  private Integer level;
  @Schema(description = "accessToken")
  private String accessToken;
  @Schema(description = "refreshToken")
  private String refreshToken;

  @Builder
  public AuthResponseDto(Member member, String accessToken, String refreshToken) {
    this.memberId = member.getMemberId();
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
