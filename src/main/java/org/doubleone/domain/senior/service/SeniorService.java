package org.doubleone.domain.senior.service;

import lombok.RequiredArgsConstructor;
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

  // 등록
  public SeniorRequestDto registerSenior(SeniorRequestDto seniorRequestDto) {
    Senior senior = seniorRequestDto.toEntity();
    senior = seniorRepository.save(senior);
    return SeniorRequestDto.fromEntity(senior);
  }

  // 수정
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

  // 삭제
  public void deleteSenior(Long seniorId) {
    if (!seniorRepository.existsById(seniorId)) {
      throw new CustomException(ErrorCode.SENIOR_NOT_FOUND);
    }
    seniorRepository.deleteById(seniorId);
  }

  // 목록 조회
  @Transactional(readOnly = true)
  public List<SeniorResponseDto> getSeniorList() {
    List<Senior> seniors = seniorRepository.findAll();

    return seniors.stream()
            .map(SeniorResponseDto::new)
            .collect(Collectors.toList());
  }

  // 상세 조회
  @Transactional(readOnly = true)
  public SeniorResponseDto getSeniorDetail(Long seniorId) {
    Senior senior = seniorRepository.findById(seniorId)
            .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));
    return new SeniorResponseDto(senior);
  }

  // 필터 조회
  @Transactional(readOnly = true)
  public List<SeniorResponseDto> getSeniorListWithFilter(String name, Integer age, String gender, String region) {
    List<Senior> seniors = seniorRepository.findAll().stream()
            .filter(senior -> name == null || senior.getName().contains(name))
            .filter(senior -> age == null || calculateAge(senior.getBirthDate()) == age)
            .filter(senior -> gender == null || senior.getGender().name().equalsIgnoreCase(gender))
            .filter(senior -> region == null || senior.getAddress().contains(region))
            .collect(Collectors.toList());

    return seniors.stream()
            .map(SeniorResponseDto::new)
            .collect(Collectors.toList());
  }

  private int calculateAge(LocalDate birthDate) {
    return LocalDate.now().getYear() - birthDate.getYear();
  }
}
