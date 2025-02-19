package org.doubleone.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.member.dto.request.*;
import org.doubleone.domain.member.dto.response.AuthResponseDto;
import org.doubleone.domain.member.dto.response.EmailVerificationResponseDto;
import org.doubleone.domain.member.dto.response.LoginResponseDto;
import org.doubleone.domain.member.dto.response.TokenResponseDto;
import org.doubleone.domain.member.service.AuthService;
import org.doubleone.domain.member.service.EmailSenderService;
import org.doubleone.domain.member.service.KakaoService;
import org.doubleone.domain.member.service.MemberService;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final MemberService memberService;
  private final EmailSenderService emailSenderService;
  private final KakaoService kakaoService;

  @Operation(summary = "토큰 재발급", description = "refreshToken을 통해 accessToken을 재발급")
  @PostMapping("/token")
  public ResponseEntity<TokenResponseDto> reissuedAccessToken(@RequestBody TokenRequestDto requestDto) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.reissueAccessToken(requestDto.refreshToken()));
  }

  @Operation(summary = "이메일 회원가입 (관리자)")
  @PostMapping("/signup/managers")
  public ResponseEntity<?> signupManager(@RequestBody SignupManagerDto request) {
    authService.signUpManager(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "이메일 회원가입 (요양사)")
  @PostMapping("/signup/workers")
  public ResponseEntity<?> signupWorker(@RequestBody SignupWorkerDto request) {
    authService.signUpWorker(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "카카오 회원가입 (관리자)")
  @PostMapping("/signup/managers/kakao")
  public ResponseEntity<?> signupManagerForKakao(@RequestBody SignupManagerForKakaoDto requestDto) {
    authService.signUpManagerForKakao(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "카카오 회원가입 (요양사)")
  @PostMapping("/signup/workers/kakao")
  public ResponseEntity<?> signupWorkerForKakao(@RequestBody SignupWorkerForKakaoDto requestDto) {
    authService.signUpWorkerForKakao(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "이메일 로그인")
  @PostMapping("/login")
  public ResponseEntity<?> signIn(@RequestBody LoginRequestDto loginRequestDto) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
  }

  @Operation(summary = "카카오 로그인")
  @PostMapping("/login/kakao")
  public ResponseEntity<?> signInForKakao(@RequestBody LoginForKakaoRequestDto loginRequestDto) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
  }

  @Operation(summary = "kakao에 정보 요청 메서드", description = "카카오 로그인 정보를 바탕으로 토큰 및 멤버 정보 반환")
  @PostMapping("/login/token")
  public LoginResponseDto login(@RequestBody AuthRequestDto authRequestDto) {
    return kakaoService.signIn(authRequestDto.getCode(), authRequestDto.getRedirectUri());
  }

  @Operation(summary = "개인정보 수정", description = "회원이 개인정보를 수정")
  @PatchMapping("/members/profile")
  public ResponseEntity<Void> updateProfile(
          @AuthenticationPrincipal UserDetails userDetails,
          @RequestBody MemberProfileUpdateRequestDto requestDto
  ) {
    String memberEmail = userDetails.getUsername();
    memberService.updateProfile(memberEmail, requestDto);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "센터 정보 확인", description = "기업 회원가입 특정 키워드로 검색해 센터 정보 확인")
  @GetMapping("/center")
  public ResponseEntity<?> getCentersByKeyword(@RequestParam String keyword) {
    return ResponseEntity.ok(authService.getCentersByKeyword(keyword));
  }

  @Operation(summary = "회원 탈퇴", description = "회원을 INACTIVE 상태로 변경")
  @GetMapping("/deactivate/{memberId}")
  public ResponseEntity<?> deactivate(@PathVariable("memberId") Long memberId) {
    authService.deactivateMember(memberId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "이메일 인증번호 전송", description = "이메일 인증번호를 전송")
  @PostMapping("/email/send")
  public ResponseEntity<EmailVerificationResponseDto> sendVerificationEmail(@Valid @RequestBody EmailVerificationRequestDto requestDto) throws MessagingException {
    emailSenderService.sendVerificationEmail(requestDto.getEmail());
//    return ResponseEntity.ok(new EmailVerificationResponseDto(true, "인증번호 전송 완료"));
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Operation(summary = "이메일 인증번호 확인", description = "이메일 인증번호를 확인")
  @GetMapping("/email/verify")
  public ResponseEntity<EmailVerificationResponseDto> verifyEmail(@Valid @RequestBody EmailVerificationRequestDto verificationRequestDto) {
    boolean isValid = emailSenderService.verifyCode(verificationRequestDto.getEmail(), verificationRequestDto.getVerificationCode());
    if (isValid) {
      return ResponseEntity.status(HttpStatus.OK).build();
    } else {
      throw new CustomException(ErrorCode.INCORRECT_VERIFICATION_CODE);
    }
  }
}
