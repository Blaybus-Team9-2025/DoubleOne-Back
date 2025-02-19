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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.global.BaseTimeEntity;
import java.time.LocalDate;

@Entity
@Table(name = "worker")
@Getter
@Log4j2
@Builder
@AllArgsConstructor
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

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "profile_img", columnDefinition = "TEXT")
    private String profileImg;

    @Column(name = "gender")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

  @Column(name = "birth_date")
  @NotNull
  private LocalDate birthDate;

  @Column(name = "phone_num", unique = true)
  @NotNull
  private String phoneNum;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "zipcode")
    @NotNull
    private String zipcode;

    @Column(name = "detail_address")
    @NotNull
    private String detailAddress;

    @Column(name = "has_trained")
    @NotNull
    private boolean hasTrained;

    @Column(name = "has_vehicle")
    @NotNull
    private boolean hasVehicle;

  @Builder
  public Worker(String name, Member member, LocalDate birthDate, org.doubleone.domain.worker.entity.Gender gender, String phoneNum, String address, String zipcode, String detailAddress, boolean hasTrained, boolean hasVehicle) {
      this.name = name;
      this.profileImg = null;
      this.member = member;
      this.birthDate = birthDate;
      this.gender = gender;
      this.phoneNum = phoneNum;
      this.address = address;
      this.zipcode = zipcode;
      this.detailAddress = detailAddress;
      this.hasTrained = hasTrained;
      this.hasVehicle = hasVehicle;
  }

  // 기본 정보 수정
  public void updateWorker(String phoneNum, String address, String detailAddress, String zipcode) {
      this.phoneNum = phoneNum;
      this.address = address;
      this.detailAddress = detailAddress;
      this.zipcode = zipcode;
  }

  public void updateProfileImg(String profileImg) {
    this.profileImg = profileImg;
  }

  public void deactivateMember() {
    this.name = "(알 수 없음)";
    this.profileImg = null;
  }
}
