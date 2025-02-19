package org.doubleone.domain.workerRegion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.region.entity.Region;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "worker_region")
@Getter
@Log4j2
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerRegion extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "worker_region_id", updatable = false)
  private Long workerRegionId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "worker_condition_id", updatable = false)
  @NotNull
  @JsonIgnore
  private WorkerCondition workerCondition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "region_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Region region;

  public void updateRegion(Region region) {
    this.region = region;
  }

  // 여기에 추가
  public void setWorkerCondition(WorkerCondition workerCondition) {
    this.workerCondition = workerCondition;
    workerCondition.getWorkerRegions().add(this); // 양방향 관계 설정
  }
}
