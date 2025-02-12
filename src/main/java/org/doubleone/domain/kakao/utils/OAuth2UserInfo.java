/*
package org.doubleone.domain.kakao.utils;

import java.util.Map;

public class OAuth2UserInfo {

    private final Map<String, Object> attributes;

    // OAuth2UserInfo 객체 생성자
    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    // 사용자 고유 카카오 ID 반환 메서드
    public Long getKakaoId() {
        // attributes 맵에서 "id" 필드를 가져옴
        Long kakaoId = ((Number) attributes.get("id")).longValue();
        return kakaoId;
    }

    // 사용자 닉네임 반환 메서드
    public String getNickname() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return (String) profile.get("nickname");
    }
    // 사용자 이메일 반환 메서드
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    public String getProfileImage() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return (String) profile.get("profile_image_url");
    }

}
*/
