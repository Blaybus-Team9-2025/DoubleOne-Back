package org.doubleone.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "member")
@Getter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id", updatable = false)
  private Long memberId;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "phone_num", unique = true)
  private String phoneNum;

  @Column(name = "password")
  private String password;

  @Column(name = "profile_img", columnDefinition = "TEXT")
  private String profileImg;

  @Column(name = "member_type")
  @NotNull
  @Enumerated(EnumType.STRING)
  private MemberType memberType;

  @Column(name = "member_status")
  @NotNull
  @Enumerated(EnumType.STRING)
  private MemberStatus memberstatus;

  @Column(name = "address")
  private String address;

  @Builder
  public Member(String email, String password, MemberType memberType) {
    this.email = email;
    this.password = password;
    this.memberstatus = MemberStatus.ACTIVE;
    this.memberType = MemberType.UNKNOWN;
  }

  @Builder
  public Member(String email, String password) {
    this.email = email;
    this.password = password;
    this.memberstatus = MemberStatus.ACTIVE;
    this.memberType = MemberType.UNKNOWN;
  }

  public void updateProfileImg(String profileImg) {
    this.profileImg = profileImg;
  }

  public void updatePassword(String password) {
    this.password = password;
  }

  public void updatePhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public void updateAddress(String address) {
    this.address = address;
  }


}
