package org.doubleone.domain.worker.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

@Getter
@NoArgsConstructor
public class WorkerDetailResponse {
    private Long workerId;
    private String gender;
    private String phoneNum;
    private boolean hasTrained;
    private boolean hasVehicle;
    private String address;
    private String license;
    private List<String> workerConditions;
    private List<String> workerLicenses;
    private List<String> workerRegions;
    private List<String> workerSchedules;

    public WorkerDetailResponse(
        Worker worker,
        List<WorkerCondition> conditions,
        List<WorkerLicense> licenses,
        List<WorkerRegion> regions,
        List<WorkerSchedule> schedules
    ) {
        this.workerId = worker.getWorkerId();
        this.gender = String.valueOf(worker.getGender());
        this.phoneNum = worker.getPhoneNum();
        this.hasTrained = worker.isHasTrained();
        this.hasVehicle = worker.isHasVehicle();
        this.address = worker.getAddress();
        this.license = worker.getLicense();

        this.workerConditions = conditions.stream()
            .map(condition -> "급여: " + condition.getWage() + ", 소개: " + condition.getIntroduce())
            .collect(Collectors.toList());

        this.workerLicenses = licenses.stream()
            .map(WorkerLicense::toString)
            .collect(Collectors.toList());

        this.workerRegions = regions.stream()
            .map(WorkerRegion::toString)
            .collect(Collectors.toList());

        this.workerSchedules = schedules.stream()
            .map(WorkerSchedule::toString)
            .collect(Collectors.toList());
    }
}
