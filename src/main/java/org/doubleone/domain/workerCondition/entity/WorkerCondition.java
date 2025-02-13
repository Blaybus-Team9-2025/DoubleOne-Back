package org.doubleone.domain.workerCondition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

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

  @Column(name = "introduce")
  private String introduce;

  @Column(name = "work_periods", columnDefinition = "json")
  @Type(JsonType.class)
  private List<WorkPeriod> workPeriods;

  @OneToMany(mappedBy = "workerCondition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WorkerRegion> workerRegions;

  @OneToMany(mappedBy = "workerCondition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WorkerSchedule> workerSchedules;

  public void update(WageType wageType, int wage, String introduce, List<WorkPeriod> workPeriods) {
    this.wageType = wageType;
    this.wage = wage;
    this.introduce = introduce;
    this.workPeriods = workPeriods;
  }
}
