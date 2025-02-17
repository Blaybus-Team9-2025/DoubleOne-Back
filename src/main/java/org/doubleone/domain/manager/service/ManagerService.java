package org.doubleone.domain.manager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.dto.ManagerProfileUpdateRequestDto;
import org.doubleone.domain.manager.dto.ManagerUpdateRequestDto;
import org.doubleone.domain.manager.dto.SeniorMatchingResponseDto;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.repository.MatchingRepository;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

  private final ManagerRepository managerRepository;
  private final MemberRepository memberRepository;
  private final MatchingRepository matchingRepository;
  private final PasswordEncoder passwordEncoder;

  // 개인정보 수정
  public void updateProfile(String managerEmail, ManagerProfileUpdateRequestDto requestDto) {
    Member member = memberRepository.findByEmail(managerEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Manager manager = managerRepository.findByMember(member)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (requestDto.getProfileImg() != null) {
      manager.updateProfileImg(requestDto.getProfileImg());
    }
    if (requestDto.getPhoneNum() != null) {
      manager.updatePhoneNum(requestDto.getPhoneNum());
    }
    if (requestDto.getAddress() != null) {
      manager.updateAddress(requestDto.getAddress());
    }
    if (requestDto.getPassword() != null && requestDto.getPasswordConfirm() != null) {
      if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
        throw new CustomException(ErrorCode.INVALID_REQUEST);
      }
      member.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
    }
  }

  // 센터정보 수정
  public void updateCenterInfo(ManagerUpdateRequestDto requestDto) {
    Member member = memberRepository.findById(requestDto.getMemberId())
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Manager manager = managerRepository.findByMember(member)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (requestDto.getCenterPeriod() != null) {
      manager.updateCenterPeriod(requestDto.getCenterPeriod());
    }
    if (requestDto.getCenterMessage() != null) {
      manager.updateCenterMessage(requestDto.getCenterMessage());
    }
    if (requestDto.getCenterGrade() != null) {
      manager.updateCenterGrade(requestDto.getCenterGrade());
    }
    if (requestDto.getHasTruck() != null) {
      manager.updateHasTruck(requestDto.getHasTruck());
    }
  }

  // 현재 매칭 중인 어르신 목록 조회
  @Transactional(readOnly = true)
  public List<SeniorMatchingResponseDto> getMatchingSeniors() {
    List<Matching> matchingList = matchingRepository.findByMatchingStatus(MatchingStatus.IN_PROGRESS);

    return matchingList.stream()
            .map(matching -> {
              Senior senior = matching.getCondition().getSenior();
              return SeniorMatchingResponseDto.of(senior, matchingList.size());
            })
            .distinct()
            .collect(Collectors.toList());
  }
}
