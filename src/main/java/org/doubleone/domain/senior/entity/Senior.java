package org.doubleone.domain.senior.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.manager.entity.Gender;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.List;

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
  private CareLevel careLevel;

  @Column(name = "weight")
  @NotNull
  private int weight;

  @Column(name = "address")
  @NotNull
  private String address;

  @Column(name = "profile_img", columnDefinition = "TEXT")
  private String profileImg;

  @Column(name = "cohabitation_status")
  @NotNull
  @Enumerated(EnumType.STRING)
  private CohabitationStatus cohabitationStatus;

  @Column(name = "dementia_symptoms", columnDefinition = "json")
  @Type(JsonType.class)
  private List<String> dementiaSymptoms;

  @Column(name = "etc_disease")
  private String etcDisease;

  public void update(CareLevel careLevel, String address, String profileImg, String etcDisease) {
    if (careLevel != null) this.careLevel = careLevel;
    if (address != null) this.address = address;
    if (profileImg != null) this.profileImg = profileImg;
    if (etcDisease != null) this.etcDisease = etcDisease;
  }
}
