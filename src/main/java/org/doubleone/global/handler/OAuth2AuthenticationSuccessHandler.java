package org.doubleone.global.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.member.dto.response.LoginResponseDto;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.doubleone.global.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

  @Slf4j
  @Component
  @RequiredArgsConstructor
  public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final WorkerRepository workerRepository;
    private final ManagerRepository managerRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
      // OAuth2 인증된 사용자 정보 가져오기
      DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

      // 사용자 속성에서 이메일 추출
      String email = (String) oAuth2User.getAttributes().get("email");

      // email을 통해 데이터베이스에서 Member 조회
      Member member = memberRepository.findByEmail(email)
          .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

      // Worker, Manager 조회
      Worker worker = workerRepository.findByMember(member);
      Manager manager = managerRepository.findByMember(member).orElse(null);

      Long workerId = (worker != null) ? worker.getWorkerId() : null;
      Long managerId = (manager != null) ? manager.getManagerId() : null;

      // AccessToken, RefreshToken 발급
      String accessToken = tokenProvider.createAccessToken(member);
      String refreshToken = tokenProvider.createRefreshToken(member);

      // 리프레시 토큰 저장
      tokenProvider.saveRefreshToken(member.getMemberId(), refreshToken);

      LoginResponseDto loginResponse = LoginResponseDto.from(accessToken, refreshToken, member, workerId, managerId);

      // JSON 응답 설정
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      // 응답 데이터 반환
      response.getWriter().write(String.valueOf(loginResponse));
      response.getWriter().flush();
    }

}
