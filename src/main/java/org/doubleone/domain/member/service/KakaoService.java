package org.doubleone.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.member.dto.request.KakaoTokenRequestDto;
import org.doubleone.domain.member.dto.response.KakaoProfileResponseDto;
import org.doubleone.domain.member.dto.response.KakaoTokenResponseDto;
import org.doubleone.domain.member.dto.response.LoginResponseDto;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.entity.MemberType;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.global.feign.KakaoApiFeign;
import org.doubleone.global.feign.KakaoAuthFeign;
import org.doubleone.global.jwt.TokenProvider;
import org.doubleone.global.utils.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
  private static final String GRANT_TYPE_VALUE = "authorization_code";
  private static final String BEARER = "Bearer ";

  private final MemberRepository memberRepository;
  private final WorkerRepository workerRepository;
  private final ManagerRepository managerRepository;
  private final TokenProvider tokenProvider;
  private final KakaoApiFeign kakaoApiFeign;
  private final KakaoAuthFeign kakaoAuthFeign;

  @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
  private String clientId;

  public LoginResponseDto signIn(String code, String redirectUri) {
    String kakaoToken = getKakaoToken(code, redirectUri);
    Member member = getKakaoProfile(kakaoToken);

    String accessToken = tokenProvider.createAccessToken(member);
    String refreshToken = tokenProvider.createRefreshToken(member);

    Worker worker = workerRepository.findOptionalByMember(member).orElse(null);
    Manager manager = managerRepository.findOptionalByMember(member).orElse(null);

    Long workerId = (worker != null) ? worker.getWorkerId() : null;
    Long managerId = (manager != null) ? manager.getManagerId() : null;

    return LoginResponseDto.builder()
        .memberId(member.getMemberId())
        .memberType(member.getMemberType())
        .managerId(workerId)
        .workerId(managerId)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  private String getKakaoToken(String code, String redirectUri) {
    KakaoTokenResponseDto tokenResponseDto = kakaoAuthFeign.getKakaoToken(
        KakaoTokenRequestDto.builder()
            .grant_type(GRANT_TYPE_VALUE)
            .client_id(clientId)
            .redirect_uri(redirectUri)
            .code(code)
            .build()
    );
    return tokenResponseDto.getAccess_token();
  }

  private Member getKakaoProfile(String kakaoToken) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.AUTHORIZATION, BEARER + kakaoToken);
    KakaoProfileResponseDto kakaoProfile = kakaoApiFeign.getKakaoProfile(httpHeaders);

    Long loginId = kakaoProfile.getId();
    String email = kakaoProfile.getEmail();

    Member member = memberRepository.findByEmail(email).orElse(null);
    if (member != null) {
      return member;
    }
    else {
      member = Member.builder()
          .email(email)
          .password("")
          .memberType(MemberType.UNKNOWN)
          .build();
      return memberRepository.save(member);
    }
  }
}
