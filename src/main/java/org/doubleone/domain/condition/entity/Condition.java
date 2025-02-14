package org.doubleone.domain.condition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.workerSchedule.entity.SeniorSchedule;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "senior_condition")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Condition extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "senior_condition_id", updatable = false)
  private Long seniorConditionId;

  @ManyToOne(fetch = FetchType.LAZY) //, cascade = CascadeType.ALL 삭제
  @JoinColumn(name = "senior_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Senior senior;

  @Column(name = "wage")
  @NotNull
  private int wage;

  @OneToMany(mappedBy = "condition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SeniorSchedule> seniorSchedules;

  @Column(name = "welfares", columnDefinition = "json")
  @Type(JsonType.class)
  private Map<String, List<String>> welfares;

  @Column(name = "service_type")
  @NotNull
  @Enumerated(EnumType.STRING)
  private ServiceType serviceType;

  @Column(name = "services", columnDefinition = "json")
  @Type(JsonType.class)
  private Map<String, List<String>> services;

  // 근무 조건 등록용
  public static Condition createCondition(Senior senior, int wage, List<SeniorSchedule> seniorSchedules, Map<String, List<String>> welfares, ServiceType serviceType, Map<String, List<String>> services) {
    Condition condition = new Condition();
    condition.senior = senior;
    condition.wage = wage;
    condition.welfares = welfares;
    condition.serviceType = serviceType;
    condition.services = services;
    if (seniorSchedules != null) {
      for (SeniorSchedule schedule : seniorSchedules) {
        schedule.setCondition(condition); // 이 부분 추가
      }
    }
    condition.seniorSchedules = seniorSchedules;
    return condition;
  }


  // 근무 조건 편집용
  public void updateCondition(int wage, Map<String, List<String>> welfares, ServiceType serviceType, Map<String, List<String>> services) {
    this.wage = wage;
    this.welfares = welfares;
    this.serviceType = serviceType;
    this.services = services;
  }

}
