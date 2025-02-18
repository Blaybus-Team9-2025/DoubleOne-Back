package org.doubleone.domain.matching.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "matching")
@Getter
@Log4j2
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "matching_id", updatable = false)
  private Long matchingId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "senior_condition_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Condition condition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "worker_condition_id", updatable = false)
  @NotNull
  @JsonIgnore
  private WorkerCondition workerCondition;

  @Column(name = "matching_status")
  @NotNull
  @Enumerated(EnumType.STRING)
  private MatchingStatus matchingStatus;

  @Column(name = "running_status")
  @NotNull
  @Enumerated(EnumType.STRING)
  private RunningStatus runningStatus;

  public void updateStatus(MatchingStatus matchingStatus, RunningStatus runningStatus) {
    this.matchingStatus = matchingStatus;
    this.runningStatus = runningStatus;
  }
}
