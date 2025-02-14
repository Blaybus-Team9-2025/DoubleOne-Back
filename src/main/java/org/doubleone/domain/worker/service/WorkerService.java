package org.doubleone.domain.worker.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.worker.dto.WorkerDetailResponse;
import org.doubleone.domain.worker.dto.WorkerUpdateRequest;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.repository.WorkerConditionRepository;
import org.doubleone.domain.workerLicense.entity.WorkerLicenseRepository;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerRegion.repository.WorkerRegionRepository;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.doubleone.domain.workerSchedule.repository.WorkerScheduleRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {
  private final WorkerRepository workerRepository;

  private final WorkerRepository workerRepository;
  private final WorkerRepository workerRepository;
  private final WorkerConditionRepository workerConditionRepository;
  private final WorkerLicenseRepository workerLicenseRepository;
  private final WorkerRegionRepository workerRegionRepository;
  private final WorkerScheduleRepository workerScheduleRepository;
    private final WorkerRepository workerRepository;
    private final WorkerConditionRepository workerConditionRepository;
    private final WorkerLicenseRepository workerLicenseRepository;
    private final WorkerRegionRepository workerRegionRepository;
    private final WorkerScheduleRepository workerScheduleRepository;


    // 요양사 정보 수정
    @Transactional
    public void updateWorker(Long workerId, WorkerUpdateRequest request) {
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));
        worker.updateWorkerInfo(request.getPhoneNum(), request.getAddress(),
            request.isHasTrained(), request.isHasVehicle(), request.getLicense());
    }

    // 요양사 상세정보 조회
    @Transactional(readOnly = true)
    public WorkerDetailResponse getWorkerDetail(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));

        List<WorkerCondition> conditions = workerConditionRepository.findByWorker(worker);
        List<WorkerLicense> license = workerLicenseRepository.findByWorker(worker);
        List<WorkerRegion> regions = workerRegionRepository.findByWorker(worker);
        List<WorkerSchedule> schedules = workerScheduleRepository.findByWorker(worker);

        return WorkerDetailResponse.from(worker, conditions, license, regions, schedules);
    }


}
