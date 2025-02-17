package org.doubleone.domain.condition.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.entity.WorkType;
import org.doubleone.domain.senior.dto.SeniorScheduleDto;
import org.doubleone.domain.senior.entity.Senior;

import java.util.List;
import java.util.Map;

public record ConditionRequestDto (
        int wage,
        List<SeniorScheduleDto> seniorSchedules,
        Map<String, List<String>> welfares,
        WorkType workType,
        Map<String, List<String>> services
){
    public Condition toEntity(Senior senior){
        return Condition.builder()
                .senior(senior)
                .wage(wage)
                .welfares(welfares)
                .workType(workType)
                .services(services)
                .build();
    }
}