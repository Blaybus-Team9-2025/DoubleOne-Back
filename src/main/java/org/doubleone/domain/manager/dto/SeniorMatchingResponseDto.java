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
    private int matchingCount; // 매칭중인 요양사 수

    public static SeniorMatchingResponseDto of(Senior senior, int matchingCount) {
        return SeniorMatchingResponseDto.builder()
                .seniorId(senior.getSeniorId())
                .name(senior.getName())
                .address(senior.getAddress())
                .profileImg(senior.getProfileImg())
                .matchingCount(matchingCount)
                .build();
    }
}
