package org.doubleone.domain.senior.dto;

import lombok.Builder;
import lombok.Data;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.senior.entity.Benefit;
import org.doubleone.domain.senior.entity.CareService;
import org.doubleone.domain.senior.entity.CohabitationStatus;
import org.doubleone.domain.senior.entity.SeniorCare;

import java.util.List;
import java.util.Set;

@Data
public class SeniorCareDto {

    private CohabitationStatus cohabitationStatus;
    private Set<CareService> careServices;
    private Integer hourlyWage;
    private Integer monthlyWage;
    private String schedules;
    private Set<Benefit> benefits;
    @Builder
    public SeniorCareDto(CohabitationStatus cohabitationStatus,
                      Set<CareService> careServices,
                      Integer hourlyWage,
                      Integer monthlyWage,
                      String schedules,
                      Set<Benefit> benefits) {
        this.cohabitationStatus = cohabitationStatus;
        this.careServices = careServices;
        this.hourlyWage = hourlyWage;
        this.monthlyWage = monthlyWage;
        this.schedules = schedules;
        this.benefits = benefits;
    }
    public SeniorCare toEntity() {
        return SeniorCare.builder()
                .cohabitationStatus(this.cohabitationStatus)
                .careServices(this.careServices)
                .hourlyWage(this.hourlyWage)
                .monthlyWage(this.monthlyWage)
                .schedules(this.schedules)
                .benefits(this.benefits)
                .build();

    }
}
