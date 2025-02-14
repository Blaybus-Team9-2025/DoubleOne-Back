package org.doubleone.domain.matching.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "senior_condition_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Condition condition;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
