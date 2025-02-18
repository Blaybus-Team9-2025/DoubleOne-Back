package org.doubleone.domain.worker.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.worker.dto.request.WorkerUpdateRequest;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.worker.dto.response.WorkerDetailResponse;
import org.doubleone.domain.worker.dto.response.WorkerLicenseDto;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.worker.entity.Gender;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerCondition.repository.WorkerConditionRepository;
import org.doubleone.domain.workerLicense.repository.WorkerLicenseRepository;
import org.doubleone.domain.workerRegion.repository.WorkerRegionRepository;
import org.doubleone.domain.workerSchedule.repository.WorkerScheduleRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.doubleone.global.utils.S3Util;
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
    private final S3Util s3Util;

    //매칭된 요양사 찾기
    public List<WorkerCondition> getMatchedWorkerBySenior(Senior senior, Condition condition) {
        String[] addressParts = senior.getAddress().split(" ");
        String district = addressParts[addressParts.length - 2]; // "강남구"
        String neighborhood = addressParts[addressParts.length - 1]; // "역삼동"
        Gender prefer = condition.getPreferGender();//선호하는 성별
        boolean hasDementiaSymptoms = !senior.getDementiaSymptoms().contains("false");//치매라면 FALSE, 치매가 아니면 TRUE
        return workerConditionRepository.findWorkerByMatchingSchedule(neighborhood, district, prefer, hasDementiaSymptoms);
    }

    // 요양사 정보 수정
    public void updateWorker(WorkerUpdateRequest workerUpdateRequest) {
        Worker worker = workerRepository.findById(workerUpdateRequest.workerId())
            .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));

        if (workerUpdateRequest.imgFile() != null && !workerUpdateRequest.imgFile().isEmpty()) {
            if (worker.getProfileImg() != null && !worker.getProfileImg().isEmpty()) {
                s3Util.deleteImage(worker.getProfileImg());
            }
            worker.updateProfileImg(s3Util.uploadImage(workerUpdateRequest.imgFile(), "profile/worker"));
        }

        worker.updateWorker(
            workerUpdateRequest.phoneNum(),
            workerUpdateRequest.address(),
            workerUpdateRequest.hasVehicle(),
            workerUpdateRequest.hasTrained()
        );
        if (workerUpdateRequest.password() != null && workerUpdateRequest.passwordConfirm() != null) {
            if (!workerUpdateRequest.password().equals(workerUpdateRequest.passwordConfirm())) {
                throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
            }
            Member member = worker.getMember(); // member
            member.updatePassword(passwordEncoder.encode(workerUpdateRequest.password()));
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
        List<ScheduleDto> schedules = workerScheduleRepository.findByWorkerCondition(workerCondition).stream()
            .map(ScheduleDto::from)
            .collect(Collectors.toList());
        return WorkerDetailResponse.from(worker, workerCondition, licenses, regions, schedules);
    }


}
