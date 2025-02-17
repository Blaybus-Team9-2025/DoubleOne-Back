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
import org.doubleone.domain.member.dto.request.MemberProfileUpdateRequestDto;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.doubleone.global.utils.S3Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

  private final ManagerRepository managerRepository;
  private final MemberRepository memberRepository;
  private final MatchingRepository matchingRepository;
  private final PasswordEncoder passwordEncoder;
  private final S3Util s3Util;

//  // 개인정보 수정
//  public void updateProfile(String managerEmail, MemberProfileUpdateRequestDto requestDto) {
//    Member member = memberRepository.findByEmail(managerEmail)
//        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//
//    Manager manager = managerRepository.findByMember(member)
//        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//
//    if (requestDto.getProfileImg() != null) {
//      manager.updateProfileImg(requestDto.getProfileImg());
//    }
//    if (requestDto.getPhoneNum() != null) {
//      manager.updatePhoneNum(requestDto.getPhoneNum());
//    }
//    if (requestDto.getAddress() != null) {
//      manager.updateAddress(requestDto.getAddress());
//    }
//    if (requestDto.getPassword() != null && requestDto.getPasswordConfirm() != null) {
//      if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
//        throw new CustomException(ErrorCode.INVALID_REQUEST);
//      }
//      member.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
//    }
//  }

  public void updateProfile(MultipartFile imgFile, ManagerUpdateRequestDto requestDto) {
    Long memberId = requestDto.getMemberId();

    Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Manager manager = managerRepository.findByMember(member)
            .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));

    // 프로필 이미지 수정
    if (imgFile != null){
      if (manager.getProfileImg() != null) {
        s3Util.deleteImage(manager.getProfileImg());}
      manager.updateProfileImg(s3Util.uploadImage(imgFile, "profile/manager"));
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
  public void updateCenterInfo(MultipartFile imgFile,CenterUpdateRequestDto requestDto) {
    Manager manager = managerRepository.findById(requestDto.managerId())
            .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));

    // 센터 이미지 수정
    if (imgFile != null){
      if (manager.getCenterImg() != null) {
        s3Util.deleteImage(manager.getCenterImg());}
      manager.updateCenterImg(s3Util.uploadImage(imgFile, "profile/center"));
    }

    // 영업기간 수정
    if (requestDto.centerPeriod() != null) {
      manager.updateCenterPeriod(requestDto.centerPeriod());
    }

    // 센터 한마디 수정
    if (requestDto.centerMessage() != null) {
      manager.updateCenterMessage(requestDto.centerMessage());
    }

    // 센터 등급 수정
    if (requestDto.centerGrade() != null) {
      manager.updateCenterGrade(requestDto.centerGrade());
    }

    // 목욕 차량 소유 여부 수정
    if (requestDto.hasTruck() != null) {
      manager.updateHasTruck(requestDto.hasTruck());
    }
    if (requestDto.getAddress() != null) {
      manager.updateAddress(requestDto.getAddress());
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
