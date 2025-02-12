package org.doubleone.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.login.LoginDto;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.entity.MemberType;
import org.doubleone.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final ManagerRepository managerRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signUp(LoginDto request) {
    // 이메일 중복 체크
    if (memberRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("이미 사용 중인 이메일입니다.");
    }

    //멤버에 기본정보 저장(이름,이메일,비번)
    Member member = Member.builder()
            .email(request.getEmail())
            .name(request.getName())
            .memberType(MemberType.MANAGER)
            .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
            .build();
    memberRepository.save(member);

    //관리사 정보 저장
    Manager manager = Manager.builder()
            .centerName(request.getCenterName())
            .phoneNum(request.getPhoneNum())
            .address(request.getAddress())
            .hasTruck(request.isHasTruck())
            .centerGrade(request.getCenterGrade())
            .centerPeriod(request.getCenterPeriod())
            .centerGrade(request.getCenterGrade())
            .member(member)
            .build();
    managerRepository.save(manager);
  }
}
