package org.doubleone.domain.workerCondition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
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
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "worker_condition")
@Getter
@Log4j2
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

  @Column(name = "wage")
  @NotNull
  private int wage;

  @Column(name = "introduce")
  private String introduce;

  @Column(name = "work_periods", columnDefinition = "json")
  @Type(JsonType.class)
  private Map<String, List<String>> workPeriods;
}
