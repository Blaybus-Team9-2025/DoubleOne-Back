package org.doubleone.domain.worker.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.worker.dto.WorkPeriodDto;
import org.doubleone.domain.worker.dto.WorkerDetailDto;

import java.util.List;

@Getter
@Builder
public class WorkerMatchResponseDto {
    private String seniorName;
    private String seniorAddress;
    private int age;
    private String profileImg;
    private List<WorkerDetailDto> workers;
}
