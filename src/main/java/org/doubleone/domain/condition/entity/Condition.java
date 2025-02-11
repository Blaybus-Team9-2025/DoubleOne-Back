package org.doubleone.domain.condition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.global.BaseTimeEntity;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "condition")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Condition extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "condition_id", updatable = false)
  private Long conditionId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "senior_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Senior senior;

  @Column(name = "wage")
  @NotNull
  private int wage;

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

}
