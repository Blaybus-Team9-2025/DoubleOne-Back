package org.doubleone.domain.matching.dto.request;

import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.entity.RunningStatus;

public record MatchingUpdateRequestDto(
    Long matchingId,
    MatchingStatus matchingStatus,
    RunningStatus runningStatus
) {

}
