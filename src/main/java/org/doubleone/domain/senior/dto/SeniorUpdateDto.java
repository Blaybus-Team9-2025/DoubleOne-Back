package org.doubleone.domain.senior.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeniorUpdateDto {
    private String careLevel;
    private String address;
    private String detailedAddress;
    private String profileImg;
    private String etcDisease;
}
