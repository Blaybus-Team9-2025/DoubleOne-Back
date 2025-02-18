package org.doubleone.domain.condition.dto;

import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.entity.WorkType;
import org.doubleone.domain.senior.dto.SeniorScheduleDto;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.worker.entity.Gender;

import java.util.List;
import java.util.Map;

public record ConditionRequestDto (
        int wage,
        List<SeniorScheduleDto> seniorSchedules,
        Map<String, List<String>> welfares,
        WorkType workType,

        Gender preferGender,
        Map<String, List<String>> services
){
    public Condition toEntity(Senior senior){
        return Condition.builder()
                .senior(senior)
                .wage(wage)
                .welfares(welfares)
                .workType(workType)
                .preferGender(preferGender)
                .services(services)
                .build();
    }
}