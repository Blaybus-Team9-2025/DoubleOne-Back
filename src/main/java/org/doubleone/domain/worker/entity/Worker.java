package org.doubleone.domain.worker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.manager.entity.Gender;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "worker")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Worker extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "worker_id", updatable = false)
  private Long workerId;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Member member;

  @Column(name = "gender")
  @NotNull
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "phone_num", unique = true)
  @NotNull
  private String phoneNum;

  @Column(name = "has_trained")
  @NotNull
  private boolean hasTrained;

  @Column(name = "has_vehicle")
  @NotNull
  private boolean hasVehicle;

  @Column(name = "address")
  @NotNull
  private String address;

  @Column(name = "license")
  @NotNull
  private String license;

  @Transactional
  public void updateWorkerInfo(String phoneNum, String address, boolean hasTrained, boolean hasVehicle, String license) {
    if (phoneNum != null) this.phoneNum = phoneNum;
    if (address != null) this.address = address;
    this.hasTrained = hasTrained;
    this.hasVehicle = hasVehicle;
    if (license != null) this.license = license;
  }
}
