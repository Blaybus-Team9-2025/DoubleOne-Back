package org.doubleone.domain.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "schedule")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "schedule_id", updatable = false)
  private Long scheduleId;

  @Column(name = "day")
  @NotNull
  @Enumerated(EnumType.STRING)
  private Day day;

  @Column(name = "start_date")
  @NotNull
  private LocalDate startDate;

  @Column(name = "end_date")
  @NotNull
  private LocalDate endDate;

}
