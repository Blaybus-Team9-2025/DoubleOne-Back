package org.doubleone.domain.workerCondition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "worker_condition")
@Getter
@Log4j2
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerCondition extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "worker_condition_id", updatable = false)
  private Long workerConditionId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "worker_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Worker worker;

  @Column(name = "wageType")
  @NotNull
  @Enumerated(EnumType.STRING)
  private WageType wageType;

  @Column(name = "wage")
  @NotNull
  private int wage;

  @Column(name = "discuss")
  @NotNull
  private boolean discuss;

  @Column(name = "introduce")
  private String introduce;

  @Column(name = "has_trained")
  @NotNull
  private boolean hasTrained;

  @Column(name = "has_vehicle")
  @NotNull
  private boolean hasVehicle;

  @Column(name = "services", columnDefinition = "json")
  @Type(JsonType.class)
  private Map<String, List<String>> services;

  @Column(name = "work_periods", columnDefinition = "json")
  @Type(JsonType.class)
  private List<WorkPeriod> workPeriods;

  @Builder.Default
  @OneToMany(mappedBy = "workerCondition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WorkerLicense> workerLicenses = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "workerCondition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WorkerRegion> workerRegions = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "workerCondition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WorkerSchedule> workerSchedules = new ArrayList<>();

  public void update(WageType wageType, int wage, String introduce, List<WorkPeriod> workPeriods) {
    if (wageType != null) this.wageType = wageType;
    if (wage != 0) this.wage = wage;
    if (introduce != null) this.introduce = introduce;
    if (workPeriods != null) this.workPeriods = workPeriods;
  }
}
