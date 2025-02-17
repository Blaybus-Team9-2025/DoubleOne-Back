package org.doubleone.domain.worker.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;

@Getter
@Builder
public class WorkerDetailResponse {
    private Long workerId;
    private String name;
    private String gender;
    private String phoneNum;
    private boolean hasTrained;
    private boolean hasVehicle;
    private String address;
    private String introduction;
    private Long memberId;
    private List<WorkerLicenseDto> workerLicenses;
    private List<WorkerRegionDto> workerRegions;
    private List<ScheduleDto> workerSchedules;

    public static WorkerDetailResponse from(
        Worker worker,
        WorkerCondition workerCondition,
        List<WorkerLicenseDto> licenses,
        List<WorkerRegionDto> regions,
        List<ScheduleDto> schedules
    ) {
        return WorkerDetailResponse.builder()
            .workerId(worker.getWorkerId()) // Id
            .name(worker.getName())  // 이름
            .gender(String.valueOf(worker.getGender())) // 성별
            .phoneNum(worker.getPhoneNum())
            .hasTrained(workerCondition.isHasTrained())
            .hasVehicle(workerCondition.isHasVehicle())
            .address(worker.getAddress())
            .introduction(workerCondition.getIntroduce())
            .memberId(worker.getMember().getMemberId())
            .workerLicenses(licenses)
            .workerRegions(regions)
            .workerSchedules(schedules)
            .build();
    }
}
