package org.doubleone.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailVerificationResponseDto {
    private boolean success;
    private String message;

}
