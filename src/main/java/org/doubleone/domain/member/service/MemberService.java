package org.doubleone.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.global.BaseTimeEntity;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional(readOnly = true)
  public Member getCurrentMember() throws CustomException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Member member = memberRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));
    return member;
  }

  public Member getMemberById(Long memberId){
    return memberRepository.findById(memberId).get();
  }


}
