package org.doubleone.domain.senior.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.manager.entity.Gender;
import org.doubleone.domain.manager.entity.Manager;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "senior")
@Getter
@Setter
@Log4j2
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
  private int care_level;

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
  private Map<String, Object> dementiaSymptoms;

}
