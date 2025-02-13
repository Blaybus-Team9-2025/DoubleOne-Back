package org.doubleone.domain.member.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.doubleone.global.utils.OAuth2UserInfo;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // OAuth2 사용자 정보 로드
    OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

    // OAuth2UserInfo 객체 생성하여 kakaoId 추출
    OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(oAuth2User.getAttributes());

    // email이 null인지 확인
    if (oAuth2UserInfo.getEmail() == null) {
      throw new CustomException(ErrorCode.UNAUTHORIZED);
    }

    // DB에서 email로 사용자 조회, 없으면 새로 생성
    Member member = memberRepository.findByEmail(oAuth2UserInfo.getEmail())
        .orElseGet(() -> createMember(oAuth2UserInfo));

    // 사용자 속성 생성
    Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
    attributes.put("id", member.getMemberId());
    attributes.put("email", member.getEmail());

    // DefaultOAuth2User 객체 생성하여 반환
    return new DefaultOAuth2User(
        Collections.singleton(new OAuth2UserAuthority(attributes)),
        attributes,
        "email"); // 기본 식별자 지정
  }

  // 사용자 생성 메서드
  private Member createMember(OAuth2UserInfo oAuth2UserInfo) {
    Member member = Member.builder()
        .email(oAuth2UserInfo.getEmail())
        .password("")
        .build();
    return memberRepository.save(member);
  }
}

