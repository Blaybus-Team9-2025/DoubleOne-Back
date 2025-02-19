package org.doubleone.global.feign;

import org.doubleone.domain.member.dto.response.KakaoProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoApiFeign", url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoApiFeign {

  @GetMapping
  KakaoProfileResponseDto getKakaoProfile(@RequestHeader HttpHeaders headers);
}
