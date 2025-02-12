package org.doubleone.global.utils;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuth2UserInfo {
  private final Map<String, Object> attributes;

  // OAuth2UserInfo 객체 생성자
  public OAuth2UserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  // 사용자 이메일 반환 메서드
  public String getEmail() {
    return (String) ((Map) attributes.get("kakao_account")).get("email");
  }
}
