package org.doubleone.domain.condition.dto;

import lombok.Builder;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.entity.PayType;
import org.doubleone.domain.condition.entity.WorkType;
import org.doubleone.domain.senior.dto.SeniorScheduleDto;
import org.doubleone.domain.senior.entity.Senior;

import java.util.List;
import java.util.Map;

@Builder
public record ConditionRequestDto(
        String title,
        int wage,
        Integer amount,
        PayType payType,
        List<SeniorScheduleDto> seniorSchedules,
        Map<String, List<String>> welfares,
        WorkType workType,
        Map<String, List<String>> services
) {
    public Condition toEntity(Senior senior) {
        return Condition.builder()
                .senior(senior)
                .title(title)
                .wage(wage)
                .amount(amount)
                .payType(payType)
                .welfares(welfares)
                .workType(workType)
                .services(services)
                .build();
    }
}
