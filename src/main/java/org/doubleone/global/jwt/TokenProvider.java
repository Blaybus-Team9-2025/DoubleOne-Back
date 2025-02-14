package org.doubleone.global.jwt;

import io.jsonwebtoken.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

  // application.yml에 저장한 jwt값 가져오기
  @Value("${jwt.secretKey}")
  private String secretKey;

  // 토큰 만료시간 설정
  private static final long ACCESS_TOKEN_EXPIRE_TIME =
      1000 * 60 * 60L; // 1시간
  private static final long REFRESH_TOKEN_EXPIRE_TIME =
      1000 * 60 * 60 * 25 * 14L; // 2주

  // 토큰에 포함할 기본 정보와 클레임 키값 설정
  private static final String AUTH_CLAIM = "auth";

  private final MemberRepository memberRepository;
  private final RedisTemplate<String, String> redisTemplate;

  public Map<String, String> createTokens(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));
    Date now = new Date();
    // Access Token 생성
    String accessToken = Jwts.builder()
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // JWT 타입 지정
        .setIssuedAt(now) // 발급 시간
        .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // 만료 시간
        .setSubject(authentication.getName()) // 사용자 이름 (sub)
        .claim(AUTH_CLAIM, authorities) // 권한 정보 (auth)
        .signWith(SignatureAlgorithm.HS256, secretKey) // 서명
        .compact();
    // Refresh Token 생성
    String refreshToken = Jwts.builder()
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // JWT 타입 지정
        .setIssuedAt(now) // 발급 시간
        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME)) // 만료 시간
        .setSubject(authentication.getName()) // 사용자 이름 (sub)
        .signWith(SignatureAlgorithm.HS256, secretKey) // 서명
        .compact();
    // Map을 반환
    Map<String, String> tokens = new HashMap<>();
    tokens.put("grantType", "Bearer");
    tokens.put("accessToken", accessToken);
    tokens.put("refreshToken", refreshToken);
    return tokens;
  }
  /**
   * AccessToken 생성 메소드 사용자 이메일 정보를 포함해 AccessToken 생성
   */
  public String createAccessToken(Member member) {
    Date now = new Date();
    // 액세스토큰 발급
    return Jwts.builder()
        // 헤더 - 토큰 타입: JWT
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        // 내용 - 토큰이 발급된 시간: 현재 시간
        .setIssuedAt(now)
        // 내용 - 토큰 만료 시간: expiredMs 변수값
        .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
        // 내용 - 토큰 제목: 사용자 이메일
        .setSubject(member.getEmail())
        // 서명 - 시크릿키와 함께 해시값을 HS256 방식으로 암호화
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  /**
   * RefreshToken 생성 메소드
   */
  public String createRefreshToken(Member member) {
    Date now = new Date();
    return Jwts.builder()
        // 헤더 - 토큰 타입: JWT
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        // 내용 - 토큰이 발급된 시간: 현재 시간
        .setIssuedAt(now)
        // 내용 - 토큰 만료 시간: expiredMs 변수값
        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
        // 내용 - 토큰 제목: 사용자 이메일
        .setSubject(member.getEmail())
        // 서명 - 시크릿키와 함께 해시값을 HS256 방식으로 암호화
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  // key: 사용자 memberId, value: 리프레시 토큰 리프레시토큰 만료 시간
  public void saveRefreshToken(Long memberId, String refreshToken) {
    // Redis에 리프레시 토큰 저장
    redisTemplate.opsForValue()
        .set(memberId.toString(), refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME));
  }

  // email 클레임 값 추출
  public String extractEmail(String accessToken) {
    if (isValidToken(accessToken)) {
      return getClaims(accessToken).getSubject();
    }
    return null;
  }

  // 유효한 토큰인지 검증
  public boolean isValidToken(String token) {
    try {
      // secretKey를 사용해 토큰 복호화
      Jwts.parser()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      // Token이 잘못된 경우
      throw new CustomException(ErrorCode.INVALID_TOKEN);
    } catch (ExpiredJwtException e) {
      // 토큰이 만료된 경우
      throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
    } catch (UnsupportedJwtException e) {
      // 지원하지 않는 토큰 형식인 경우
      throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
    } catch (SignatureException e) {
      // 서명 오류인 경우
      throw new CustomException(ErrorCode.INVALID_SIGNATURE);
    } catch (IllegalArgumentException e) {
      // JWT 클레임 문자열이 비어있는 경우
      throw new CustomException(ErrorCode.EMPTY_CLAIMS);
    }
  }

  // 토큰에서 사용자 인증 정보를 꺼내 반환
  // JWT 토큰의 클레임에서 사용자 이메일과 권한 정보를 추출하여 Authentication 객체 생성
  public Authentication getAuthentication(String token) {
    // 토큰 복호화
    Claims claims = getClaims(token);

    // 토큰에서 정보를 꺼냄
    Set<SimpleGrantedAuthority> authorities = Collections
        .singleton(new SimpleGrantedAuthority("ROLE_USER"));

    return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails
        .User(claims.getSubject(), "", authorities), token, authorities);
  }

  // 토큰 복호화 후 페이로드 반환
  private Claims getClaims(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getPayload();
  }
}
