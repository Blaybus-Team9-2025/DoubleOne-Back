package org.doubleone.domain.senior.service;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.senior.dto.SeniorRequestDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.entity.CareLevel;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.doubleone.global.utils.S3Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class SeniorService {

  private final SeniorRepository seniorRepository;
  private final S3Util s3Util;
  private final ManagerRepository managerRepository;

  // 등록
  public void registerSenior(SeniorRequestDto seniorRequestDto) {
    Manager manager = managerRepository.findById(seniorRequestDto.managerId())
        .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));
    Senior senior = seniorRequestDto.toEntity(manager, null);
    if (seniorRequestDto.imgFile() != null && !seniorRequestDto.imgFile().isEmpty()) {
      if (senior.getProfileImg() != null && !senior.getProfileImg().isEmpty()) {
        s3Util.deleteImage(senior.getProfileImg());
      }
      senior.updateProfileImg(s3Util.uploadImage(seniorRequestDto.imgFile(), "profile/senior"));
    }
    seniorRepository.save(senior);
  }

  public void updateSenior(SeniorUpdateDto seniorUpdateDto) {
    Senior senior = seniorRepository.findById(seniorUpdateDto.seniorId())
            .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));
    if (seniorUpdateDto.imgFile() != null){
      if (senior.getProfileImg() != null) {
        s3Util.deleteImage(senior.getProfileImg());}
      senior.updateProfileImg(s3Util.uploadImage(seniorUpdateDto.imgFile(), "profile/senior"));
    }

    senior.update(
            CareLevel.valueOf(seniorUpdateDto.careLevel().toUpperCase()),
            seniorUpdateDto.address(),
            seniorUpdateDto.etcDisease()
    );
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
}
