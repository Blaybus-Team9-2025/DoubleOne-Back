package org.doubleone.domain.worker.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.worker.dto.WorkPeriodDto;

import java.util.List;

@Getter
@Builder
public class WorkerMatchResponseDto {
    private Long workerId;
    private String workerName;
    private List<WorkerRegionDto> workerRegions;
    private String seniorName;
    private String seniorAddress;
    private String organization;
}
