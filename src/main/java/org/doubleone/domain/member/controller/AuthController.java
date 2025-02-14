package org.doubleone.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.member.dto.request.LoginRequestDto;
import org.doubleone.domain.member.dto.request.SignupManagerDto;
import org.doubleone.domain.member.dto.request.SignupManagerForKakaoDto;
import org.doubleone.domain.member.dto.request.SignupWorkerForKakaoDto;
import org.doubleone.domain.member.dto.request.TokenRequestDto;
import org.doubleone.domain.member.dto.request.SignupWorkerDto;
import org.doubleone.domain.member.dto.response.TokenResponseDto;
import org.doubleone.domain.member.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/token")
  public ResponseEntity<TokenResponseDto> reissuedAccessToken(@RequestBody TokenRequestDto requestDto){
    return ResponseEntity.status(HttpStatus.OK).body(authService.reissueAccessToken(requestDto.refreshToken()));
  }

  @PostMapping("/signup/managers")
  public ResponseEntity<?> signupManager(@RequestBody SignupManagerDto request) {
    authService.signUpManager(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/signup/workers")
  public ResponseEntity<?> signupWorker(@RequestBody SignupWorkerDto request) {
    authService.signUpWorker(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/signup/managers")
  public ResponseEntity<?> signupManagerForKakao(@RequestBody SignupManagerForKakaoDto requestDto) {
    authService.signUpManagerForKakao(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/signup/workers/kakao")
  public ResponseEntity<?> signupWorkerForKakao(@RequestBody SignupWorkerForKakaoDto requestDto) {
    authService.signUpWorkerForKakao(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<?> signIn(@RequestBody LoginRequestDto loginRequestDto) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
  }
}
