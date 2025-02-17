package org.doubleone.domain.worker.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.worker.dto.request.WorkerUpdateRequest;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.worker.dto.response.WorkerDetailResponse;
import org.doubleone.domain.worker.dto.response.WorkerLicenseDto;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.worker.dto.response.WorkerScheduleDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerCondition.repository.WorkerConditionRepository;
import org.doubleone.domain.workerLicense.repository.WorkerLicenseRepository;
import org.doubleone.domain.workerRegion.repository.WorkerRegionRepository;
import org.doubleone.domain.workerSchedule.repository.WorkerScheduleRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    //매칭된 요양사 찾기
    public List<WorkerCondition> getMatchedWorkerBySenior(Senior senior) {
        String[] addressParts = senior.getAddress().split(" ");
        String district = addressParts[addressParts.length - 2]; // "강남구"
        String neighborhood = addressParts[addressParts.length - 1]; // "역삼동"

        return workerConditionRepository.findWorkerByMatchingSchedule(neighborhood, district);
    }

//    // 요양사 정보 수정
//    @Transactional
//    public void updateWorker(Long workerId, WorkerUpdateRequest request) {
//        Worker worker = workerRepository.findById(workerId)
//            .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));
//        worker.updateWorkerInfo(request.getPhoneNum(), request.getAddress(),
//            request.isHasTrained(), request.isHasVehicle(), request.getLicense());
//    }

    // 요양사 정보 수정
    public void updateWorker(Long workerId, WorkerUpdateRequest workerUpdateRequest) {
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));

        worker.updateWorker(
            workerUpdateRequest.getProfileImg(),
            workerUpdateRequest.getPhoneNum(),
            workerUpdateRequest.getAddress(),
            workerUpdateRequest.isHasVehicle(),
            workerUpdateRequest.isHasTrained()
        );
        if (workerUpdateRequest.getPassword() != null && workerUpdateRequest.getPasswordConfirm() != null) {
            if (!workerUpdateRequest.getPassword().equals(workerUpdateRequest.getPasswordConfirm())) {
                throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
            }
            Member member = worker.getMember(); // member
            member.updatePassword(passwordEncoder.encode(workerUpdateRequest.getPassword()));
        }
    }

    // 요양사 상세정보 조회
    @Transactional(readOnly = true)
    public WorkerDetailResponse getWorkerDetail(Long workerConditionId) {
        WorkerCondition workerCondition = workerConditionRepository.findById(workerConditionId)
            .orElseThrow(() -> new CustomException(ErrorCode.WORKER_CONDITION_NOT_FOUND));
        Worker worker = workerCondition.getWorker();
        List<WorkerLicenseDto> licenses = workerLicenseRepository.findByWorkerCondition(workerCondition).stream()
            .map(WorkerLicenseDto::from)
            .collect(Collectors.toList());
        List<WorkerRegionDto> regions = workerRegionRepository.findByWorkerCondition(workerCondition).stream()
            .map(WorkerRegionDto::from)
            .collect(Collectors.toList());
        List<WorkerScheduleDto> schedules = workerScheduleRepository.findByWorkerCondition(workerCondition).stream()
            .map(WorkerScheduleDto::from)
            .collect(Collectors.toList());
        return WorkerDetailResponse.from(worker, workerCondition, licenses, regions, schedules);
    }


}
