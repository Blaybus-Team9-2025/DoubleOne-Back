/*
package org.doubleone.domain.kakao.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.kakao.utils.OAuth2UserInfo;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    //로그인 요청 들어오면 실행되는 메서드. 사용자정보 OAuth2User로 변환
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2 사용자 정보 로드
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // OAuth2UserInfo 객체 생성하여 kakaoId 추출
        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(oAuth2User.getAttributes());
        Long kakaoId = oAuth2UserInfo.getKakaoId();

        // DB에서 kakaoId로 사용자 조회, 없으면 새로 생성
        Member member = memberRepository.findByKakaoId(kakaoId);
        if(member == null) member = createmember(oAuth2UserInfo);

        // 사용자 속성 생성
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("kakaoId", kakaoId); // kakaoId를 명시적으로 추가

        // DefaultOAuth2User 객체 생성하여 반환
        return new DefaultOAuth2User(
                Collections.singleton(new OAuth2UserAuthority(attributes)),
                attributes,
                "kakaoId"); // 기본 식별자를 "kakaoId"로 설정
    }

    */
/**
     * 사용자 생성 메서드
     *//*

    private Member createmember(OAuth2UserInfo oAuth2UserInfo) {
        Member member = Member.builder()
                .kakaoId(oAuth2UserInfo.getKakaoId())
                .email(oAuth2UserInfo.getEmail())
                .password("")
                .name(oAuth2UserInfo.getNickname())
                .build();
        return memberRepository.save(member);
    }
}*/
