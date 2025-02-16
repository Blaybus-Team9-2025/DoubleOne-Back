package org.doubleone.domain.condition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.entity.SeniorSchedule;
import org.doubleone.domain.workerCondition.entity.WorkPeriod;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "senior_condition")
@Getter
@Builder
@Log4j2
@AllArgsConstructor
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

  @Column(name = "work_type")
  @NotNull
  @Enumerated(EnumType.STRING)
  private WorkType workType;

  @Column(name = "services", columnDefinition = "json")
  @Type(JsonType.class)
  private Map<String, List<String>> services;

//  @Column(name = "services", columnDefinition = "json")
//  @Type(JsonType.class)
//  private List<WorkPeriod> workPeriods;

  // 근무 조건 등록용
  public static Condition createCondition(Senior senior, int wage, Map<String, List<String>> welfares, WorkType workType, Map<String, List<String>> services,List<SeniorSchedule> seniorSchedules) {
    Condition condition = new Condition();
    condition.senior = senior;
    condition.wage = wage;
    condition.welfares = welfares;
    condition.workType = workType;
    condition.services = services;
    condition.seniorSchedules = seniorSchedules;
    return condition;
  }

  // 근무 조건 편집용
  public void updateCondition(int wage, Map<String, List<String>> welfares, WorkType workType, Map<String, List<String>> services) {
    this.wage = wage;
    this.welfares = welfares;
    this.workType = workType;
    this.services = services;
  }

}
