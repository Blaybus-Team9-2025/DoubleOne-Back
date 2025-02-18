package org.doubleone.domain.matching.dto.request;

import java.util.List;
import org.doubleone.domain.schedule.dto.ScheduleDto;

public record WorkerMatchingScheduleRequestDto(
    Long endMatchingId,
    List<ScheduleDto> scheduleDtoList
) {
}
