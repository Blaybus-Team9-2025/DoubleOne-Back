package org.doubleone.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.global.BaseTimeEntity;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  // 개발 진행을 위한 임시 메서드
  public Member getCurrentMember(){
    return memberRepository.findById((long)1).get();
  }

  public Member getOtherMember(){
    return memberRepository.findById((long)2).get();
  }

  public Member getMemberById(Long memberId){
    return memberRepository.findById(memberId).get();
  }


}
