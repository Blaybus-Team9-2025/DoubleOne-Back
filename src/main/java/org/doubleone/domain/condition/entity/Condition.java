package org.doubleone.domain.condition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.worker.entity.Gender;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.entity.SeniorSchedule;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "senior_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Senior senior;

  @Column(name = "title") // 공고 제목
  @NotNull
  private String title;

  @Column(name = "amount") // 급여 금액
  @NotNull
  private int amount;

  @Enumerated(EnumType.STRING) // 급여 단위
  @Column(name = "pay_type")
  @NotNull
  private PayType payType;

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

  @Column(name="prefer_gender")
  @NotNull
  @Enumerated(EnumType.STRING)
  private Gender preferGender;

  @Column(name = "services", columnDefinition = "json")
  @Type(JsonType.class)
  private Map<String, List<String>> services;

  // 근무 조건 등록용
  public static Condition createCondition(Senior senior, String title, int amount, PayType payType, int wage, Gender preferGender,
                                          Map<String, List<String>> welfares, WorkType workType,
                                          Map<String, List<String>> services, List<SeniorSchedule> seniorSchedules) {
    Condition condition = new Condition();
    condition.senior = senior;
    condition.title = title;
    condition.amount = amount;
    condition.payType = payType;
    condition.preferGender = preferGender;
    condition.wage = wage;
    condition.welfares = welfares;
    condition.workType = workType;
    condition.services = services;
    condition.seniorSchedules = seniorSchedules;
    return condition;
  }

  // 근무 조건 편집용

  public void updateCondition(String title, int amount, PayType payType, int wage,
                              Map<String, List<String>> welfares, Gender preferGender, WorkType workType,
                              Map<String, List<String>> services) {
    this.title = title;
    this.amount = amount;
    this.payType = payType;
    this.wage = wage;
    this.welfares = welfares;
    this.preferGender = preferGender;
    this.workType = workType;
    this.services = services;
  }
}
