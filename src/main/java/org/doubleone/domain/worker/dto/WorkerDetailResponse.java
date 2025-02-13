package org.doubleone.domain.worker.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

@Getter
@Builder
public class WorkerDetailResponse {
    private Long workerId;
    private String gender; // 수정불가
    private String phoneNum;
    private boolean hasTrained;
    private boolean hasVehicle;
    private String address;
    private String license;
    private List<String> workerConditions;
    private List<String> workerLicenses;
    private List<String> workerRegions;
    private List<String> workerSchedules;

    public static WorkerDetailResponse from(
        Worker worker,
        List<WorkerCondition> conditions,
        List<WorkerLicense> licenses,
        List<WorkerRegion> regions,
        List<WorkerSchedule> schedules
    ) {
        return WorkerDetailResponse.builder()
            .workerId(worker.getWorkerId())
            .gender(String.valueOf(worker.getGender()))
            .phoneNum(worker.getPhoneNum())
            .hasTrained(worker.isHasTrained())
            .hasVehicle(worker.isHasVehicle())
            .address(worker.getAddress())
            .license(worker.getLicense())
            .workerConditions(conditions.stream()
                .map(condition -> "급여: " + condition.getWage() + "소개: " + condition.getIntroduce())
                .collect(Collectors.toList()))
            .workerLicenses(licenses.stream()
                .map(WorkerLicense::toString)
                .collect(Collectors.toList()))
            .workerRegions(regions.stream()
                .map(WorkerRegion::toString)
                .collect(Collectors.toList()))
            .workerSchedules(schedules.stream()
                .map(WorkerSchedule::toString)
                .collect(Collectors.toList()))
            .build();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 67fa96a (변경사항저장)
