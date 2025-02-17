package org.doubleone.domain.manager.dto;

import org.springframework.web.multipart.MultipartFile;

public record CenterUpdateRequestDto(
    // 센터정보 수정
    Long managerId,
    Boolean hasTruck,
    String centerGrade,
    String centerPeriod,
    String centerInfo,
    String centerMessage
) {

}
