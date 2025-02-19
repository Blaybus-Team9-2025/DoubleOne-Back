package org.doubleone.domain.worker.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;

import java.util.List;

@Getter
@Builder
public class WorkerResponse {
    private Long workerId;
    private String name;
    private String gender;
    private String phoneNum;
    private boolean hasTrained;
    private boolean hasVehicle;
    private String address;
    private String zipcode;
    private String detailAddress;

    public static WorkerResponse from(
            Worker worker
    ) {
        return WorkerResponse.builder()
                .workerId(worker.getWorkerId()) // Id
                .name(worker.getName())  // 이름
                .gender(String.valueOf(worker.getGender())) // 성별
                .phoneNum(worker.getPhoneNum())
                .hasTrained(worker.isHasTrained())
                .hasVehicle(worker.isHasVehicle())
                .address(worker.getAddress())
                .zipcode(worker.getZipcode())
                .detailAddress(worker.getDetailAddress())
                .build();
    }
}
