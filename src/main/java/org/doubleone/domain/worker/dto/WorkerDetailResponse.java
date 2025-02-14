package org.doubleone.domain.worker.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.license.entity.License;
import org.doubleone.domain.worker.entity.Worker;

import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

@Getter
@Builder
public class WorkerDetailResponse {
    private Long workerId;
    private String name;  // 이름
    private String gender; // 성별 (수정불가)
    private List<String> workerRegions;  // 희망 근무 지역
    private List<String> workerSchedules;  // 희망 근무 일시
    private List<String> workerConditions;  // 급여 조건
    private String workerLicenses;  // 자격증
    private String career;  // 경력사항
    private String introduction;  // 자기소개
    private Long memberId;  // 채팅용 memberId

    public static WorkerDetailResponse from(
        Worker worker,
        List<WorkerCondition> conditions,
        List<WorkerLicense> licenses,
        List<WorkerRegion> regions,
        List<WorkerSchedule> schedules
    ) {
        return WorkerDetailResponse.builder()
            .workerId(worker.getWorkerId()) // Id
            .name(worker.getName())  // 이름
            .gender(String.valueOf(worker.getGender())) // 성별
            .workerRegions(regions.stream() // 지역
                .map(region -> region.getRegion().getCity())
                .collect(Collectors.toList()))
            .workerSchedules(schedules.stream() // 근무일정
                .map(schedule -> schedule.getSchedule().getStartTime() + schedule.getSchedule()
                    .getEndTime())
                .collect(Collectors.toList()))
            .workerConditions(conditions.stream() // 급여
                .map(condition -> condition.getWageType() + "" + condition.getWage() + "원")
                .collect(Collectors.toList()))
            .workerLicenses(worker.getLicense()) // 자격증
            .introduction( // 소개
                conditions.stream()
                    .map(WorkerCondition::getIntroduce)
                    .findFirst()
                    .orElse(null)  // introduce가 없으면 null 반환
            )
            .memberId(worker.getMember().getMemberId()) // memberId
            .build();
    }
}
