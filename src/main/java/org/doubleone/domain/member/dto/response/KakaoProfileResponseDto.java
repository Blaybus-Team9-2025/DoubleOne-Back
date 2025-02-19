package org.doubleone.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class KakaoProfileResponseDto {

  private Long id;
  private KakaoAccount kakao_account;

  @Setter
  @NoArgsConstructor
  public class KakaoAccount {

    @Schema(description = "카카오 계정 이메일")
    private String email;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return kakao_account.email;
  }
}
