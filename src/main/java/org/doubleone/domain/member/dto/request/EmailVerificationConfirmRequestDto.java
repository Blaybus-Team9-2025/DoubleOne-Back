package org.doubleone.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailVerificationConfirmRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String verificationCode;
}
