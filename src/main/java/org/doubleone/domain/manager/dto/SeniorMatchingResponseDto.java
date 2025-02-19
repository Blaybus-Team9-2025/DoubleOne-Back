package org.doubleone.domain.manager.dto;

import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.senior.entity.Senior;

@Getter
@Builder
public class SeniorMatchingResponseDto {
    private Long seniorId;
    private String name;
    private String address;
    private String profileImg;
    private Long seniorConditionId;

    public static SeniorMatchingResponseDto of(Senior senior, Condition condition) {
        return SeniorMatchingResponseDto.builder()
                .seniorId(senior.getSeniorId())
                .name(senior.getName())
                .address(senior.getAddress())
                .profileImg(senior.getProfileImg())
                .seniorConditionId(condition.getConditionId())
                .build();
    }
}
