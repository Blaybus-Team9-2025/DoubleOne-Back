package org.doubleone.domain.senior.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeniorUpdateDto {
    private String careLevel;
    private String detailedAddress;  // getAddress() → getDetailedAddress()로 수정
    private String profileImg;
    private String etcDisease;
}
