package org.doubleone.domain.senior.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "senior_care")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//구인 정보 엔티티
public class SeniorCare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_id", updatable = false)
    private Long seniorCareId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "senior_id", updatable = false)
    @NotNull
    @JsonIgnore
    private Senior senior;

    @Column(name = "cohabitation_status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private CohabitationStatus cohabitationStatus;

    @ElementCollection
    @CollectionTable(name = "care_services", joinColumns = @JoinColumn(name = "care_id"))
    @Column(name = "care_service")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<CareService> careServices;

    @Column(name = "hourly_wage")
    private Integer hourlyWage;  // 시급

    @Column(name = "monthly_wage")
    private Integer monthlyWage;  // 월급

    @Column(name = "schedule")
    private String schedules;

    // 복리후생
    @ElementCollection
    @CollectionTable(name = "job_benefits", joinColumns = @JoinColumn(name = "care_id"))
    @Column(name = "benefit")
    @Convert(converter = Benefit.BenefitConverter.class)
    private Set<Benefit> benefits;

    @Builder
    public SeniorCare(CohabitationStatus cohabitationStatus,
                         Set<CareService> careServices,
                         Integer hourlyWage,
                         Integer monthlyWage,
                         String schedules,
                         Set<Benefit> benefits) {
        this.cohabitationStatus = cohabitationStatus;
        this.careServices = careServices;
        this.hourlyWage = hourlyWage;
        this.schedules = schedules;
        this.monthlyWage = monthlyWage;
        this.benefits = benefits;
    }


}
