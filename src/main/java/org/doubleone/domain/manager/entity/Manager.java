package org.doubleone.domain.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.member.entity.Gender;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.global.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "manager")
@Getter
@Log4j2
@Builder
@AllArgsConstructor
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

  @Column(name = "name", unique = true)
  @NotNull
  private String name;

  @Column(name = "profile_img", columnDefinition = "TEXT")
  private String profileImg;

  @Column(name = "birth_date")
  @NotNull
  private LocalDate birthDate;

  @Column(name = "gender")
  @NotNull
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "phone_num", unique = true)
  @NotNull
  private String phoneNum;

  @Column(name = "has_truck")
  @NotNull
  private boolean hasTruck;

  @Column(name = "address")
  @NotNull
  private String address;

  @Column(name = "zipcode")
  @NotNull
  private String zipcode;

  @Column(name = "detail_address")
  @NotNull
  private String detailAddress;

  @Column(name = "center_name")
  @NotNull
  private String centerName;

  @Column(name = "center_grade")
  private String centerGrade;

  @Column(name = "center_period")
  private String centerPeriod;

  @Column(name = "center_message", length = 500)
  private String centerMessage;

  @Column(name = "center_img", columnDefinition = "TEXT")
  private String centerImg;

  @Column(name = "is_active")
  private Boolean isActive;


  // isactive가 null로 들어가는 문제 방지
  @PrePersist
  public void prePersist() {
    if (isActive == null) {
      isActive = true;
    }
  }

  @Builder
  public Manager(String name, String profileImg, Member member, LocalDate birthDate, Gender gender, String phoneNum, boolean hasTruck, String address, String zipcode, String detailAddress, String centerName, String centerGrade, String centerPeriod) {
    this.name = name;
    this.profileImg = profileImg;
    this.member = member;
    this.birthDate = birthDate;
    this.gender = gender;
    this.phoneNum = phoneNum;
    this.hasTruck = hasTruck;
    this.address = address;
    this.zipcode = zipcode;
    this.detailAddress = detailAddress;
    this.centerName = centerName;
    this.centerGrade = centerGrade;
    this.centerPeriod = centerPeriod;
    this.isActive = true;
  }


  public void updatePhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public void updateAddress(String address) {
    this.address = address;
  }

  public void updateProfileImg(String profileImg) {
    this.profileImg = profileImg;
  }

  public void updateCenterGrade(String centerGrade) {
    this.centerGrade = centerGrade;
  }

  public void updateCenterPeriod(String centerPeriod) {
    this.centerPeriod = centerPeriod;
  }

  public void updateCenterMessage(String centerMessage) {
    this.centerMessage = centerMessage;
  }

  public void updateHasTruck(Boolean hasTruck) {
    this.hasTruck = hasTruck;
  }


  public void updateCenterImg(String centerImg){this.centerImg = centerImg;}

  public void deactivateMember() {
    this.name = "(알 수 없음)";
    this.profileImg = null;
  }

  public void updateDetailAddress(String detailAddress) { this.detailAddress = detailAddress; }

  public void updateZipcode(String zipcode) { this.zipcode = zipcode;}
}
