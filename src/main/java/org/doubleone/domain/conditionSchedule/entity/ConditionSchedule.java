package org.doubleone.domain.conditionSchedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "condition_schedule")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConditionSchedule extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "condition_schedule_id", updatable = false)
  private Long conditionScheduleId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "condition_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Condition condition;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "schedule_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Schedule schedule;
}
