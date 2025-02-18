package org.doubleone.domain.senior.service;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.senior.dto.SeniorRequestDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.entity.CareLevel;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SeniorService {

  private final SeniorRepository seniorRepository;

  public void registerSenior(SeniorRequestDto seniorRequestDto) {
    Senior senior = seniorRequestDto.toEntity();

    // 추가: 등록 시 matchingStatus 기본값 설정
    senior.setMatchingStatus(MatchingStatus.BEFORE_REQUEST);

    seniorRepository.save(senior);
  }

  public SeniorRequestDto updateSenior(Long seniorId, SeniorUpdateDto seniorUpdateDto) {
    Senior senior = seniorRepository.findById(seniorId)
            .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));

    senior.update(
            CareLevel.valueOf(seniorUpdateDto.getCareLevel().toUpperCase()),
            seniorUpdateDto.getAddress(),
            seniorUpdateDto.getProfileImg(),
            seniorUpdateDto.getEtcDisease()
    );

    return SeniorRequestDto.fromEntity(senior);
  }

  public void deleteSenior(Long seniorId) {
    if (!seniorRepository.existsById(seniorId)) {
      throw new CustomException(ErrorCode.SENIOR_NOT_FOUND);
    }
    seniorRepository.deleteById(seniorId);
  }

  @Transactional(readOnly = true)
  public List<SeniorResponseDto> getSeniorList() {
    return seniorRepository.findAll().stream()
            .map(SeniorResponseDto::new)
            .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public SeniorResponseDto getSeniorDetail(Long seniorId) {
    Senior senior = seniorRepository.findById(seniorId)
            .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));
    return new SeniorResponseDto(senior);
  }

  @Transactional(readOnly = true)
  public List<SeniorResponseDto> getSeniorListWithFilter(String name, Integer age, String gender, String region) {
    return seniorRepository.findAll().stream()
            .filter(senior -> name == null || senior.getName().contains(name))
            .filter(senior -> age == null || LocalDate.now().getYear() - senior.getBirthDate().getYear() == age)
            .filter(senior -> gender == null || senior.getGender().name().equalsIgnoreCase(gender))
            .filter(senior -> region == null || senior.getAddress().contains(region))
            .map(SeniorResponseDto::new)
            .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<SeniorResponseDto> getSeniorList(String sort) {
    List<Senior> seniors;

    if ("unmatched".equals(sort)) {
      seniors = seniorRepository.findByMatchingStatusOrderByCreatedAtDesc(MatchingStatus.BEFORE_REQUEST);
    } else { // 기본: 최신순
      seniors = seniorRepository.findAllByOrderByCreatedAtDesc();
    }

    return seniors.stream()
            .map(SeniorResponseDto::new)
            .collect(Collectors.toList());
  }

}
