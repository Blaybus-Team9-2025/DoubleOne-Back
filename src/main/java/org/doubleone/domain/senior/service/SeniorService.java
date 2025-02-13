package org.doubleone.domain.senior.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.senior.dto.SeniorCareDto;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.entity.SeniorCare;
import org.doubleone.domain.senior.repository.SeniorCareRepository;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SeniorService {
  private final SeniorRepository seniorRepository;
  private final SeniorCareRepository seniorCareRepository;
  private final ObjectMapper objectMapper;
  //노인정보 저장을 위한 메서드
  public SeniorService(SeniorRepository seniorRepository, SeniorCareRepository seniorCareRepository, ObjectMapper objectMapper) {
    this.seniorRepository = seniorRepository;
    this.seniorCareRepository = seniorCareRepository;
    this.objectMapper = objectMapper;
  }
  @Transactional
  public void saveSenior(Senior senior, Manager manager) {
    senior.setManager(manager);
    seniorRepository.save(senior);
  }

  @Transactional
  public void saveSeniorCare(SeniorCareDto seniorCareDto, Senior senior)
  {
    SeniorCare seniorCare = seniorCareDto.toEntity();
    seniorCare.setSenior(senior);
    seniorCareRepository.save(seniorCare);
  }

}
