package org.doubleone.domain.senior.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.senior.dto.SeniorDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.entity.CareLevel;
import org.doubleone.domain.senior.entity.CohabitationStatus;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SeniorService {

  private final SeniorRepository seniorRepository;

  public SeniorDto registerSenior(SeniorDto seniorDto) {
    Senior senior = Senior.builder()
            .name(seniorDto.getName())
            .gender(org.doubleone.domain.manager.entity.Gender.valueOf(seniorDto.getGender().toUpperCase()))
            .birthDate(LocalDate.parse(seniorDto.getBirthDate()))
            .careLevel(CareLevel.valueOf(seniorDto.getCareLevel().toUpperCase()))
            .weight(seniorDto.getWeight())
            .zipCode(seniorDto.getZipCode())
            .detailedAddress(seniorDto.getDetailedAddress())
            .profileImg(seniorDto.getProfileImg())
            .cohabitationStatus(CohabitationStatus.valueOf(seniorDto.getCohabitationStatus().toUpperCase()))
            .dementiaSymptoms(List.of(seniorDto.getDementiaSymptoms().split(",")))
            .etcDisease(seniorDto.getEtcDisease())
            .build();

    senior = seniorRepository.save(senior);
    log.info("어르신 정보 등록 완료: {}", senior);
    return SeniorDto.fromEntity(senior);
  }

  public SeniorDto updateSenior(Long seniorId, SeniorUpdateDto seniorUpdateDto) {
    Senior senior = seniorRepository.findById(seniorId)
            .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));

    senior.update(
            CareLevel.valueOf(seniorUpdateDto.getCareLevel().toUpperCase()),
            seniorUpdateDto.getDetailedAddress(),
            seniorUpdateDto.getProfileImg(),
            seniorUpdateDto.getEtcDisease()
    );

    log.info("어르신 정보 수정 완료: {}", senior);
    return SeniorDto.fromEntity(senior);
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

}
