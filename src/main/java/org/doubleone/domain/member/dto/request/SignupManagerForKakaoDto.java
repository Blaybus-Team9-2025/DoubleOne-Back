package org.doubleone.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupManagerForKakaoDto(
    @NotNull
    Long memberId,
    @NotBlank(message="이름은 필수입니다.")
    String name,
    @NotBlank(message="전화번호는 필수입니다.")
    String phoneNum,
    @NotBlank(message="센터 이름은 필수입니다.")
    String centerName,
    @NotBlank(message="주소는 필수입니다.")
    String address,
    boolean hasTruck,
    String centerGrade,
    String centerPeriod
) {
}
