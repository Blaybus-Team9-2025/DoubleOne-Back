package org.doubleone.domain.manager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.dto.ManagerUpdateRequestDto;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

  private final ManagerRepository managerRepository;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;


  public void updateProfile(ManagerUpdateRequestDto requestDto) {
    Long memberId = requestDto.getMemberId();

    Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Manager manager = managerRepository.findByMember(member)
            .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));

    // 프로필 이미지 수정
    if (requestDto.getProfileImg() != null) {
      manager.updateProfileImg(requestDto.getProfileImg());
    }

    // 연락처 수정
    if (requestDto.getPhoneNum() != null) {
      manager.updatePhoneNum(requestDto.getPhoneNum());
    }

    // 주소 수정
    if (requestDto.getAddress() != null) {
      manager.updateAddress(requestDto.getAddress());
    }

    // 비밀번호 변경
    if (requestDto.getPassword() != null && requestDto.getPasswordConfirm() != null) {
      if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
        throw new CustomException(ErrorCode.INVALID_REQUEST);
      }
      member.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
    }
  }

  /**
   * 센터정보 수정
   */
  public void updateCenterInfo(ManagerUpdateRequestDto requestDto) {
    Long memberId = requestDto.getMemberId(); // DTO에서 memberId 받아서 사용

    Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Manager manager = managerRepository.findByMember(member)
            .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));

    // 영업기간 수정
    if (requestDto.getCenterPeriod() != null) {
      manager.updateCenterPeriod(requestDto.getCenterPeriod());
    }

    // 센터 한마디 수정
    if (requestDto.getCenterMessage() != null) {
      manager.updateCenterMessage(requestDto.getCenterMessage());
    }

    // 센터 등급 수정
    if (requestDto.getCenterGrade() != null) {
      manager.updateCenterGrade(requestDto.getCenterGrade());
    }

    // 목욕 차량 소유 여부 수정
    if (requestDto.getHasTruck() != null) {
      manager.updateHasTruck(requestDto.getHasTruck());
    }
  }
}
