package org.doubleone.domain.workerCondition.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.worker.dto.request.WorkerConditionRequestDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerCondition.repository.WorkerConditionRepository;
import org.doubleone.domain.workerRegion.service.WorkerRegionService;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
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

  public void createWorkerCondition(Long workerId, WorkerConditionRequestDto requestDto) {
    Worker worker = workerRepository.findById(workerId)
        .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));
    WorkerCondition workerCondition = workerConditionRepository.save(requestDto.toEntity(worker));
    if (requestDto.scheduleDtoList() != null && !requestDto.scheduleDtoList().isEmpty()) {
      requestDto.scheduleDtoList().forEach(scheduleDto ->
          workerScheduleService.createWorkerSchedule(workerCondition, scheduleDto));
    }
    if (requestDto.regionDtoList() != null && !requestDto.regionDtoList().isEmpty()) {
      requestDto.regionDtoList().forEach(regionDto ->
          workerRegionService.createWorkerRegion(workerCondition, regionDto));
    }
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
  }

  public void deleteWorkerCondition(Long workerConditionId) {
    workerConditionRepository.findById(workerConditionId)
        .orElseThrow(() -> new CustomException(ErrorCode.WORKER_CONDITION_NOT_FOUND));
    workerConditionRepository.deleteById(workerConditionId);
  }
}
