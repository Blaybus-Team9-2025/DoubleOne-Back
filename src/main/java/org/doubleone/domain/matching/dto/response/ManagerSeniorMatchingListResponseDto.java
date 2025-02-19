package org.doubleone.domain.matching.dto.response;

import java.util.List;

public record ManagerSeniorMatchingListResponseDto(
    List<SeniorMatchingUnitDto> seniorMatchingList
) {

}
