package org.doubleone.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.center.repository.CenterRepository;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.member.dto.request.LoginRequestDto;
import org.doubleone.domain.member.dto.request.SignupManagerDto;
import org.doubleone.domain.member.dto.request.SignupManagerForKakaoDto;
import org.doubleone.domain.member.dto.request.SignupWorkerDto;
import org.doubleone.domain.member.dto.request.SignupWorkerForKakaoDto;
import org.doubleone.domain.member.dto.response.LoginResponseDto;
import org.doubleone.domain.member.dto.response.TokenResponseDto;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.entity.MemberType;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.doubleone.global.jwt.TokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final MemberRepository memberRepository;
  private final ManagerRepository managerRepository;
  private final WorkerRepository workerRepository;
  private final TokenProvider tokenProvider;
  private final RedisTemplate<String, String> redisTemplate;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final CenterRepository centerRepository;


  public void signUpManager(SignupManagerDto requestDto) {
    // 이메일 중복 체크
    if (memberRepository.existsByEmail(requestDto.getEmail())) {
      throw new CustomException(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
    // 비밀번호 일치 검사
    if (!requestDto.getPassword().equals(requestDto.getConfirmPassword())) {
      throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
    }
    // 멤버에 기본정보 저장(이름,이메일,비번)
    Member member = Member.builder()
        .email(requestDto.getEmail())
        .password(passwordEncoder.encode(requestDto.getPassword())) // 비밀번호 암호화
        .memberType(MemberType.MANAGER)
        .build();
    memberRepository.save(member);

    // 관리자 정보 저장
    Manager manager = Manager.builder()
        .name(requestDto.getName())
        .centerName(requestDto.getCenterName())
        .phoneNum(requestDto.getPhoneNum())
        .address(requestDto.getAddress())
        .hasTruck(requestDto.isHasTruck())
        .centerGrade(requestDto.getCenterGrade())
        .centerPeriod(requestDto.getCenterPeriod())
        .centerGrade(requestDto.getCenterGrade())
        .member(member)
        .build();
    managerRepository.save(manager);
  }

  public void signUpManagerForKakao(SignupManagerForKakaoDto requestDto) {
    Member member = memberRepository.findById(requestDto.memberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    if(member.getMemberType() != MemberType.UNKNOWN){
      throw new CustomException(ErrorCode.ACCESS_DENIED);
    }
    Manager manager = Manager.builder()
        .name(requestDto.name())
        .centerName(requestDto.centerName())
        .phoneNum(requestDto.phoneNum())
        .address(requestDto.address())
        .hasTruck(requestDto.hasTruck())
        .centerGrade(requestDto.centerGrade())
        .centerPeriod(requestDto.centerPeriod())
        .centerGrade(requestDto.centerGrade())
        .member(member)
        .build();
    managerRepository.save(manager);
  }

  public void signUpWorker(SignupWorkerDto requestDto) {
    // 이메일 중복 체크
    if (memberRepository.existsByEmail(requestDto.getEmail())) {
      throw new CustomException(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
    // 멤버에 기본정보 등록
    Member member = Member.builder()
        .email(requestDto.getEmail())
        .password(passwordEncoder.encode(requestDto.getPassword())) // 비밀번호 암호화
        .memberType(MemberType.WORKER)
        .build();
    memberRepository.save(member);

    // 요양보호사 정보 저장
    Worker worker = Worker.builder()
        .name(requestDto.getName())
        .phoneNum(requestDto.getPhoneNum())
        .birthDate(requestDto.getBirthDate())
        .address(requestDto.getAddress())
        .gender(requestDto.getGender())
        .member(member)
        .build();
    workerRepository.save(worker);
  }

  public void signUpWorkerForKakao(SignupWorkerForKakaoDto requestDto) {
    Member member = memberRepository.findById(requestDto.memberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    if(member.getMemberType() != MemberType.UNKNOWN){
      throw new CustomException(ErrorCode.ACCESS_DENIED);
    }
    // 요양보호사 정보 저장
    Worker worker = Worker.builder()
        .name(requestDto.name())
        .phoneNum(requestDto.phoneNum())
        .birthDate(requestDto.birthDate())
        .address(requestDto.address())
        .gender(requestDto.gender())
        .member(member)
        .build();
    workerRepository.save(worker);
  }

  public LoginResponseDto login(LoginRequestDto requestDto){
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
    Authentication authentication =  authenticationManager.authenticate(authenticationToken);
      Member member = memberRepository.findByEmail(authentication.getName())
          .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

      // Worker, Manager 조회
      Worker worker = workerRepository.findByMemberOpt(member);
      Manager manager = managerRepository.findByMemberOpt(member).orElse(null);

      Long workerId = (worker != null) ? worker.getWorkerId() : null;
      Long managerId = (manager != null) ? manager.getManagerId() : null;

      log.info("문제 2");
      // AccessToken, RefreshToken 발급
      String accessToken = tokenProvider.createAccessToken(member);
      String refreshToken = tokenProvider.createRefreshToken(member);

      // 리프레시 토큰 저장
      tokenProvider.saveRefreshToken(member.getMemberId(), refreshToken);

      // LoginResponseDto 반환
      return LoginResponseDto.from(accessToken, refreshToken, member, workerId, managerId);

    }

  public TokenResponseDto reissueAccessToken(String refreshToken){
    // 전달받은 리프레시 토큰에서 이메일을 추출하여 사용자 정보 가져오기
    String email = tokenProvider.extractEmail(refreshToken);
    Member member = getMemberByEmail(email);
    // Redis에서 해당 사용자 Id를 키로 하는 리프래시 토큰 가져오기
    String storedRefreshToken = redisTemplate.opsForValue().get(member.getMemberId().toString());
    // 전달받은 리프레시 토큰과 Redis에 저장된 리프레시 토큰이 일치하는지 확인
    if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)){
      throw new CustomException(ErrorCode.INVALID_TOKEN);
    }
    // 일치한다면 새로운 AccessToken 생성
    String accessToken = tokenProvider.createAccessToken(member);
    return TokenResponseDto.builder()
        .accessToken(accessToken)
        .build();
  }

  // Email로 사용가 객체 가져오기
  @Transactional(readOnly = true)
  public Member getMemberByEmail(String email){
    return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 이메일로 사용자를 찾을 수 없습니다."));
  }

  @Transactional(readOnly = true)
  public List<String> getCentersByKeyword(String keyword) {
    return centerRepository.findByCenterNameContaining(keyword).stream()
        .map(center -> center.getCenterCode() + "," + center.getCenterName())
        .collect(Collectors.toList());
  }


//  public void deactivateMember(Long memberId) {
//    Member member = memberRepository.findById(memberId)
//        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//    member.deactivateMember()
//  }
}