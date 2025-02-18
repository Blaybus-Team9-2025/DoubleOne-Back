package org.doubleone.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.member.dto.request.MemberProfileUpdateRequestDto;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  public Member getMemberById(Long memberId){
    return memberRepository.findById(memberId).get();
  }

  @Transactional(readOnly = true)
  public Member getCurrentMember() throws CustomException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Member member = memberRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));
    return member;
  }

  public void updateProfile(String memberEmail, MemberProfileUpdateRequestDto requestDto) {
    Member member = memberRepository.findByEmail(memberEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (requestDto.getPassword() != null && requestDto.getPasswordConfirm() != null) {
      if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
        throw new CustomException(ErrorCode.INVALID_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      }
      member.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
    }

    // 추가: 주소 업데이트 및 매니저 테이블 반영
    if (requestDto.getAddress() != null) {
      member.updateAddress(requestDto.getAddress());

      // member에 해당하는 manager가 있는 경우 manager에도 반영
      managerRepository.findOptionalByMember(member).ifPresent(manager -> manager.updateAddress(requestDto.getAddress()));
    }
  }
}
