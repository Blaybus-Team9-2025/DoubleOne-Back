package org.doubleone.domain.workerSchedule.entity;

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
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "worker_schedule")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerSchedule extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "worker_schedule_id", updatable = false)
  private Long workerScheduleId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "worker_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Worker worker;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "schedule_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Schedule schedule;
}
