package org.doubleone.domain.senior.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.entity.Gender;
import org.doubleone.domain.senior.dto.SeniorRequestDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.entity.CareLevel;
import org.doubleone.domain.senior.entity.CohabitationStatus;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SeniorService {

  private final SeniorRepository seniorRepository;

  public SeniorRequestDto registerSenior(SeniorRequestDto seniorRequestDto) {
    Senior senior = Senior.builder()
            .name(seniorRequestDto.getName())
            .gender(Gender.valueOf(seniorRequestDto.getGender().toUpperCase()))
            .birthDate(LocalDate.parse(seniorRequestDto.getBirthDate()))
            .careLevel(CareLevel.valueOf(seniorRequestDto.getCareLevel().toUpperCase()))
            .weight(seniorRequestDto.getWeight())
            .zipCode(seniorRequestDto.getZipCode())
            .detailedAddress(seniorRequestDto.getDetailedAddress())
            .profileImg(seniorRequestDto.getProfileImg())
            .cohabitationStatus(CohabitationStatus.valueOf(seniorRequestDto.getCohabitationStatus().toUpperCase()))
            .dementiaSymptoms(List.of(seniorRequestDto.getDementiaSymptoms().split(",")))
            .etcDisease(seniorRequestDto.getEtcDisease())
            .build();

    senior = seniorRepository.save(senior);
    log.info("어르신 정보 등록 완료: {}", senior);
    return SeniorRequestDto.fromEntity(senior);
  }

  public SeniorRequestDto updateSenior(Long seniorId, SeniorUpdateDto seniorUpdateDto) {
    Senior senior = seniorRepository.findById(seniorId)
            .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));

    senior.update(
            CareLevel.valueOf(seniorUpdateDto.getCareLevel().toUpperCase()),
            seniorUpdateDto.getDetailedAddress(),
            seniorUpdateDto.getProfileImg(),
            seniorUpdateDto.getEtcDisease()
    );

    log.info("어르신 정보 수정 완료: {}", senior);
    return SeniorRequestDto.fromEntity(senior);
  }

  public void deleteSenior(Long seniorId) {
    if (!seniorRepository.existsById(seniorId)) {
      throw new CustomException(ErrorCode.SENIOR_NOT_FOUND);
    }
    seniorRepository.deleteById(seniorId);
    log.info("어르신 정보 삭제 완료: ID={}", seniorId);
  }

  @Transactional(readOnly = true)
  public List<SeniorResponseDto> getSeniorList() {
    List<Senior> seniors = seniorRepository.findAll();

    return seniors.stream()
            .map(SeniorResponseDto::new)
            .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public SeniorResponseDto getSeniorDetail(Long seniorId) {
    Senior senior = seniorRepository.findById(seniorId)
            .orElseThrow(() -> new IllegalArgumentException("해당 어르신이 존재하지 않습니다."));
    return new SeniorResponseDto(senior);
  }

  @Transactional(readOnly = true)
  public List<SeniorResponseDto> getSeniorListWithFilter(String name, Integer age, String gender, String region) {
    List<Senior> seniors = seniorRepository.findAll().stream()
            .filter(senior -> name == null || senior.getName().contains(name))
            .filter(senior -> age == null || calculateAge(senior.getBirthDate()) == age)
            .filter(senior -> gender == null || senior.getGender().name().equalsIgnoreCase(gender))
            .filter(senior -> region == null || senior.getDetailedAddress().contains(region))
            .collect(Collectors.toList());

    return seniors.stream()
            .map(SeniorResponseDto::new)
            .collect(Collectors.toList());
  }

  private int calculateAge(LocalDate birthDate) {
    return LocalDate.now().getYear() - birthDate.getYear();
  }
}
