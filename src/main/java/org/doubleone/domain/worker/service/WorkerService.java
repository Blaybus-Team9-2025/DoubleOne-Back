package org.doubleone.domain.worker.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.worker.dto.WorkerDetailResponse;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.worker.repository.WorkerConditionRepository;
import org.doubleone.domain.worker.repository.WorkerLicenseRepository;
import org.doubleone.domain.worker.repository.WorkerRegionRepository;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.worker.repository.WorkerScheduleRepository;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {

  private final WorkerRepository workerRepository;
  private final WorkerConditionRepository workerConditionRepository;
  private final WorkerLicenseRepository workerLicenseRepository;
  private final WorkerRegionRepository workerRegionRepository;
  private final WorkerScheduleRepository workerScheduleRepository;

  // 요양사 상세정보 조회
  @Transactional(readOnly = true)
  public WorkerDetailResponse getWorkerDetail(Long workerId) {
    // Worker 기본 정보 조회
    Worker worker = workerRepository.findById(workerId)
        .orElseThrow(() -> new IllegalArgumentException("해당 ID의 요양사가 존재하지 않습니다."));

    List<WorkerCondition> conditions = workerConditionRepository.findByWorkerId(workerId);
    List<WorkerLicense> license = workerLicenseRepository.findByWorkerId(workerId);
    List<WorkerRegion> regions = workerRegionRepository.findByWorkerId(workerId);
    List<WorkerSchedule> schedules = workerScheduleRepository.findByWorkerId(workerId);

    return new WorkerDetailResponse(worker, conditions, license, regions, schedules);
  }
}
