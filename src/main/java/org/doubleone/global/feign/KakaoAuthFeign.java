package org.doubleone.global.feign;

import org.doubleone.domain.member.dto.request.KakaoTokenRequestDto;
import org.doubleone.domain.member.dto.response.KakaoTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "KakaoAuthFeign", url = "https://kauth.kakao.com/oauth/token")
public interface KakaoAuthFeign {

  @PostMapping(consumes = "application/x-www-form-urlencoded")
  KakaoTokenResponseDto getKakaoToken(KakaoTokenRequestDto requestDto);
}
