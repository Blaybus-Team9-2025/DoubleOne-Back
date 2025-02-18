package org.doubleone.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.doubleone.domain.worker.entity.Gender;

public record SignupWorkerForKakaoDto(
      @NotNull
      Long memberId,
      @NotBlank(message="이름은 필수입니다.")
      String name,
      @NotBlank(message = "성별은 필수입니다.")
      Gender gender,
      @NotBlank(message = "생년월일은 필수입니다.")
      LocalDate birthDate,
      @NotBlank(message="전화번호는 필수입니다.")
      String phoneNum,
      @NotBlank(message="지번 주소는 필수입니다.")
      String address,
      @NotBlank(message="상세 주소는 필수입니다.")
      String detailAddress,
      @NotBlank(message="우편 번호는 필수입니다.")
      String zipcode
) {
}