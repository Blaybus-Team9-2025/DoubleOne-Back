//package org.doubleone.domain.kakao.handler;
//
//
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.doubleone.domain.kakao.jwt.TokenProvider;
//import org.doubleone.domain.kakao.utils.OAuth2UserInfo;
//import org.doubleone.domain.member.entity.Member;
//import org.doubleone.domain.member.repository.MemberRepository;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final TokenProvider tokenProvider;
//    private final MemberRepository memberRepository;
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        // OAuth2 인증된 사용자 정보 가져오기
//        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
//
//        // 사용자 속성에서 카카오ID 추출
//        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(oAuth2User.getAttributes());
//        Long kakaoId = oAuth2UserInfo.getKakaoId();
//        log.info("SuccessHandler - Extracted kakaoId: {}", kakaoId);
//
//        // 카카오Id를 통해 데이터베이스에서 User 엔티티 조회
//        Member member = memberRepository.findByKakaoId(kakaoId);
//
//        // AccessToken, RefreshToken 발급
//        String accessToken = tokenProvider.createAccessToken(member);
//        String refreshToken = tokenProvider.createRefreshToken(member);
//
//        // 리프레시토큰 저장
//        tokenProvider.saveRefreshToken(member.getMemberId(), refreshToken);
//
//        // JSON형식 응답 설정
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        // AccessToken, RefreshToken 토큰 전달
//        String jsonResponse = String.format("{\"accessToken\":\"%s\", \"refreshToken\":\"%s\"}", accessToken, refreshToken);
//        response.getWriter().write(jsonResponse);
//        response.getWriter().flush();
//    }
//}