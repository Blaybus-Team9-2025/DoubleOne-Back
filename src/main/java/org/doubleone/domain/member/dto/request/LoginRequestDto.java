package org.doubleone.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class LoginRequestDto {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=(.*[A-Za-z]){1,})(?=(.*\\d){1,})(?=(.*[!.?,@#$%^&*()-_+=]){1,})[A-Za-z\\d!.?,@#$%^&*()-_+=]{6,20}$",
            message = "비밀번호는 6~20자 이내이며, 영문 대소문자, 숫자, 특수문자 중 2가지 이상을 포함해야 합니다.")
    private String password;
}
