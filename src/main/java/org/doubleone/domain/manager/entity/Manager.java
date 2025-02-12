package org.doubleone.domain.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "manager")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "manager_id", updatable = false)
  private Long managerId;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id", updatable = false)
  @NotNull
  @JsonIgnore
  private Member member;

  @Column(name = "phone_num", unique = true)
  @NotNull
  private String phoneNum;

  @Column(name = "has_truck")
  @NotNull
  private boolean hasTruck;

  @Column(name = "address")
  @NotNull
  private String address;

  @Column(name = "center_name")
  @NotNull
  private String centerName;

  @Column(name = "center_grade")
  private String centerGrade;

  @Column(name = "center_period")
  private String centerPeriod;

  @Builder
  public Manager(Member member, String phoneNum, boolean hasTruck, String address, String centerName, String centerGrade, String centerPeriod) {
    this.member = member;
    this.phoneNum = phoneNum;
    this.hasTruck = hasTruck;
    this.address = address;
    this.centerName = centerName;
    this.centerGrade = centerGrade;
    this.centerPeriod = centerPeriod;
  }


}
