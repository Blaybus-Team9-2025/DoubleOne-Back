package org.doubleone.domain.condition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.entity.ServiceType;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionResponseDto {

    private Long seniorConditionId;
    private int wage;
    private Map<String, List<String>> welfares;
    private ServiceType serviceType;
    private Map<String, List<String>> services;

    public static ConditionResponseDto from(Condition condition) {
        return ConditionResponseDto.builder()
                .seniorConditionId(condition.getSeniorConditionId())
                .wage(condition.getWage())
                .welfares(condition.getWelfares())
                .serviceType(condition.getServiceType())
                .services(condition.getServices())
                .build();
    }
}
