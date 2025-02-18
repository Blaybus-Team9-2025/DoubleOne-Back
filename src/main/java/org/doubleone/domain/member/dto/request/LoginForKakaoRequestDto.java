package org.doubleone.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginForKakaoRequestDto(
    @NotBlank
    String account_email
) {

}
