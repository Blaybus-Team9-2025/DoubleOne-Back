package org.doubleone.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.doubleone.domain.member.entity.Gender;

import java.time.LocalDate;

public record SignupManagerForKakaoDto(
    @NotNull
    Long memberId,
    @NotBlank(message="이름은 필수입니다.")
    String name,
    @NotBlank(message="전화번호는 필수입니다.")
    String phoneNum,
    @NotBlank(message="센터 이름은 필수입니다.")
    String centerName,
    @NotBlank(message="지번 주소는 필수입니다.")
    String address,
    @NotBlank(message="상세 주소는 필수입니다.")
    String detailAddress,
    @NotBlank(message="우편 번호는 필수입니다.")
    String zipcode,
    @NotBlank(message="생년월일은 필수입니다.")
    LocalDate birthDate,
    @NotBlank(message="성별은 필수입니다.")
    Gender gender,
    boolean hasTruck,
    String centerGrade,
    String centerPeriod
) {
}
