package org.doubleone.domain.condition.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.entity.ServiceType;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ConditionResponseDto {

    private Long seniorConditionId;
    private int wage;
    private Map<String, List<String>> welfares;
    private ServiceType serviceType;
    private Map<String, List<String>> services;

    public ConditionResponseDto(Condition condition) {
        this.seniorConditionId = condition.getSeniorConditionId();
        this.wage = condition.getWage();
        this.welfares = condition.getWelfares();
        this.serviceType = condition.getServiceType();
        this.services = condition.getServices();
    }
}
