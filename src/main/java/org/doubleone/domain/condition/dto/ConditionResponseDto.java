package org.doubleone.domain.condition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.entity.WorkType;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionResponseDto {

    private Long seniorConditionId;
    private String title;
    private int amount;
    private String payType;
    private int wage;
    private Map<String, List<String>> welfares;
    private WorkType workType;
    private Map<String, List<String>> services;

    public static ConditionResponseDto from(Condition condition) {
        return ConditionResponseDto.builder()
                .seniorConditionId(condition.getSeniorConditionId())
                .title(condition.getTitle())
                .amount(condition.getAmount())
                .payType(condition.getPayType().name())
                .wage(condition.getWage())
                .welfares(condition.getWelfares())
                .workType(condition.getWorkType())
                .services(condition.getServices())
                .build();
    }
}
