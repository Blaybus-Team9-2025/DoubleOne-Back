package org.doubleone.domain.manager.dto;

import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.senior.entity.Senior;

@Getter
@Builder
public class SeniorMatchingResponseDto {
    private Long seniorId;
    private String name;
    private String address;
    private String profileImg;

    public static SeniorMatchingResponseDto of(Senior senior) {
        return SeniorMatchingResponseDto.builder()
                .seniorId(senior.getSeniorId())
                .name(senior.getName())
                .address(senior.getAddress())
                .profileImg(senior.getProfileImg())
                .build();
    }
}
