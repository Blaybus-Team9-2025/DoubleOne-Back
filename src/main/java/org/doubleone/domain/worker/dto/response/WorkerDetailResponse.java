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

    private String zipCode;
    private String address;
    private String detailAddress;

    private String introduction;
    private Long memberId;
    private String password; // test
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
            .zipCode(worker.getZipcode()) // 우편번호
            .address(worker.getAddress()) // 주소
            .detailAddress(worker.getDetailAddress()) // 상세주소
            .introduction(workerCondition.getIntroduce())
            .memberId(worker.getMember().getMemberId())
            .password(worker.getMember().getPassword()) // test
            .workerLicenses(licenses)
            .workerRegions(regions)
            .workerSchedules(schedules)
            .build();
    }
}
