package org.doubleone.domain.condition.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.condition.entity.ServiceType;
import org.doubleone.domain.workerSchedule.entity.SeniorSchedule;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ConditionRequestDto {
    private Long seniorId;
    private int wage;
    private List<SeniorSchedule> seniorSchedules;
    private Map<String, List<String>> welfares;
    private ServiceType serviceType;
    private Map<String, List<String>> services;


}
