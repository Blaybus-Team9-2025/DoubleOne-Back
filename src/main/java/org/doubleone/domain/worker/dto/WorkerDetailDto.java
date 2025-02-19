package org.doubleone.domain.worker.dto;

import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;

import java.util.List;

@Getter
@Builder
public class WorkerDetailDto {
    private Long workerId;
    private String workerName;
    private boolean isRequestMatching;
    private List<WorkerRegionDto> workerRegions;
    private List<WorkPeriodDto> workPeriods;
}