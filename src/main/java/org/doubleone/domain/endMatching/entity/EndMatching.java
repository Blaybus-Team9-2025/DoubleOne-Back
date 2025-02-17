package org.doubleone.domain.endMatching.entity;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "end_matching")
@Getter
@Log4j2
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EndMatching extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "end_matching_id", updatable = false)
  private Long endMatchingId;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "matching_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Matching matching;

  @Column(name = "start_date")
  @NotNull
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "schedule_id", updatable = false)
  @JsonIgnore
  private Schedule schedule;

  public void updateSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  public void updateEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
