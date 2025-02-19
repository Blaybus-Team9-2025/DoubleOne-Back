package org.doubleone.domain.workerCondition.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.worker.dto.request.WorkerConditionRequestDto;
import org.doubleone.domain.worker.dto.response.WorkerPreferenceDto;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerCondition.repository.WorkerConditionRepository;
import org.doubleone.domain.workerLicense.service.WorkerLicenseService;
import org.doubleone.domain.workerRegion.repository.WorkerRegionRepository;
import org.doubleone.domain.workerRegion.service.WorkerRegionService;
import org.doubleone.domain.workerSchedule.repository.WorkerScheduleRepository;
import org.doubleone.domain.workerSchedule.service.WorkerScheduleService;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerConditionService {

  private final WorkerConditionRepository workerConditionRepository;
  private final WorkerRepository workerRepository;
  private final WorkerScheduleService workerScheduleService;
  private final WorkerRegionService workerRegionService;
  private final WorkerLicenseService workerLicenseService;

  private final WorkerRegionRepository workerRegionRepository;
  private final WorkerScheduleRepository workerScheduleRepository;


  public Long createWorkerCondition(Long workerId, WorkerConditionRequestDto requestDto) {
    Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));

    WorkerCondition workerCondition = requestDto.toEntity(worker);
    workerConditionRepository.save(workerCondition); // 먼저 저장

    // 스케줄 등록
    if (requestDto.scheduleDtoList() != null && !requestDto.scheduleDtoList().isEmpty()) {
      requestDto.scheduleDtoList().forEach(scheduleDto ->
              workerScheduleService.createWorkerSchedule(workerCondition, scheduleDto));
    }

    // 지역 등록
    if (requestDto.regionDtoList() != null && !requestDto.regionDtoList().isEmpty()) {
      requestDto.regionDtoList().forEach(regionDto ->
              workerRegionService.createWorkerRegion(workerCondition, regionDto));
    }

    // 자격증 등록
    if (requestDto.licenseDtoList() != null && !requestDto.licenseDtoList().isEmpty()) {
      requestDto.licenseDtoList().forEach(licenseDto ->
              workerLicenseService.createWorkerLicense(workerCondition, licenseDto));
    }

    return workerCondition.getWorkerConditionId();
  }



  // 희망 근무 조건 조회
  public WorkerPreferenceDto readWorkerCondition(Long workerConditionId) {
    WorkerCondition workerCondition = workerConditionRepository.findById(workerConditionId)
        .orElseThrow(() -> new CustomException(ErrorCode.WORKER_CONDITION_NOT_FOUND));

    List<WorkerRegionDto> workerRegionDtos = workerRegionRepository.findByWorkerCondition(workerCondition).stream()
        .map(WorkerRegionDto::from)
        .toList();

    if (workerRegionDtos.isEmpty()) {
      throw new CustomException(ErrorCode.WORKER_CONDITION_NOT_FOUND);
    }

    List<ScheduleDto> workerScheduleDtos = workerScheduleRepository.findByWorkerCondition(workerCondition).stream()
        .map(ScheduleDto::from)
        .toList();

    if (workerScheduleDtos.isEmpty()) {
      throw new CustomException(ErrorCode.WORKER_CONDITION_NOT_FOUND);
    }

    WorkerRegionDto workerRegionDto = workerRegionDtos.get(0);
    ScheduleDto workerScheduleDto = workerScheduleDtos.get(0);

    return WorkerPreferenceDto.from(workerRegionDto, workerScheduleDto, workerCondition);
  }


  public void updateWorkerCondition(Long workerConditionId, WorkerConditionRequestDto requestDto) {
    WorkerCondition workerCondition = workerConditionRepository.findById(workerConditionId)
        .orElseThrow(() -> new CustomException(ErrorCode.WORKER_CONDITION_NOT_FOUND));
    workerCondition.update(requestDto.wageType(), requestDto.wage(), requestDto.introduce(), requestDto.workPeriods());
    if (requestDto.scheduleDtoList() != null && !requestDto.scheduleDtoList().isEmpty()) {
      requestDto.scheduleDtoList().forEach(scheduleDto ->
          workerScheduleService.update(workerCondition, scheduleDto));
    }
    if (requestDto.regionDtoList() != null && !requestDto.regionDtoList().isEmpty()) {
      requestDto.regionDtoList().forEach(regionDto ->
          workerRegionService.update(workerCondition, regionDto));
    }
    if (requestDto.licenseDtoList() != null && !requestDto.licenseDtoList().isEmpty()) {
      requestDto.licenseDtoList().forEach(licenseDto ->
          workerLicenseService.update(workerCondition, licenseDto));
    }
  }

  public void deleteWorkerCondition(Long workerConditionId) {
    workerConditionRepository.findById(workerConditionId)
        .orElseThrow(() -> new CustomException(ErrorCode.WORKER_CONDITION_NOT_FOUND));
    workerConditionRepository.deleteById(workerConditionId);
  }
}
