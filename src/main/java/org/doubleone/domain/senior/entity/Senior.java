package org.doubleone.domain.senior.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.manager.entity.Gender;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "senior")
@Getter
@Log4j2
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Senior extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "senior_id", updatable = false)
  private Long seniorId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "manager_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Manager manager;

  @Column(name = "name")
  @NotNull
  private String name;

  @Column(name = "birth_date")
  @NotNull
  private LocalDate birthDate;

  @Column(name = "gender")
  @NotNull
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "care_level")
  @NotNull
  @Enumerated(EnumType.STRING)
  private CareLevel careLevel; // 장기요양등급 (정책서 추가)

  @Column(name = "weight")
  @NotNull
  private int weight;

  @Column(name = "zip_code")
  @NotNull
  private String zipCode; // 우편번호 추가 (정책서 추가)

  @Column(name = "detailed_address")
  @NotNull
  private String detailedAddress; // 상세주소 추가 (정책서 추가)

  @Column(name = "profile_img", columnDefinition = "TEXT")
  private String profileImg;

  @Column(name = "cohabitation_status")
  @NotNull
  @Enumerated(EnumType.STRING)
  private CohabitationStatus cohabitationStatus;

  @Column(name = "dementia_symptoms", columnDefinition = "json")
  @Type(JsonType.class)
  private List<String> dementiaSymptoms; // 치매 증상 다중 선택 가능 (정책서 추가)

  @Column(name = "etc_disease")
  private String etcDisease; // 기타 보유 질병 (정책서 추가)

  // 어르신 정보 수정
  public void update(CareLevel careLevel, String detailedAddress, String profileImg, String etcDisease) {
    if (careLevel != null) this.careLevel = careLevel;  // CareLevel Enum 적용
    if (detailedAddress != null) this.detailedAddress = detailedAddress;
    if (profileImg != null) this.profileImg = profileImg;
    if (etcDisease != null) this.etcDisease = etcDisease;
  }

}

