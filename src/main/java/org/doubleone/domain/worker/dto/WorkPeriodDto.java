package org.doubleone.domain.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.workerCondition.entity.WorkPeriod;

@Getter
@AllArgsConstructor
@Builder
public class WorkPeriodDto {
    private String title;
    private String organization;
    private boolean isCurrent;
    private String startDate;
    private String endDate;

    public static WorkPeriodDto fromEntity(WorkPeriod workPeriod) {
        return WorkPeriodDto.builder()
                .title(workPeriod.getTitle())
                .organization(workPeriod.getOrganization())
                .isCurrent(workPeriod.isCurrent())
                .startDate(workPeriod.getStartDate())
                .endDate(workPeriod.getEndDate())
                .build();
    }
}