package org.doubleone.domain.condition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.entity.WorkType;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.senior.entity.Senior;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionResponseDto {

    private Long seniorConditionId;
    private Long seniorId;
    private String seniorName;
    private MatchingStatus matchingStatus;
    private String profileImg;


    public static ConditionResponseDto from(Condition condition, Senior senior) {
        return ConditionResponseDto.builder()
                .seniorConditionId(condition.getConditionId())
                .seniorId(senior.getSeniorId())
                .seniorName(senior.getName())
                .matchingStatus(senior.getMatchingStatus())
                .profileImg(senior.getProfileImg())
                .build();
    }
}

