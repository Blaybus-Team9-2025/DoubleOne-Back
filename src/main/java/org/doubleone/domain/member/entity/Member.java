package org.doubleone.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.global.BaseTimeEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "member")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id", updatable = false)
  private Long memberId;

  @Column(name = "kakao_id", updatable = false, unique = true)
  private Long kakaoId;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "name", unique = true)
  @NotNull
  private String name;

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


  @Builder
  public Member(Long kakaoId, String email, String password, String name, MemberType memberType) {
    this.kakaoId = kakaoId;
    this.email = email;
    this.password = password;
    this.name = name;
    this.profileImg = null;
    this.memberstatus = MemberStatus.ACTIVE;
    this.memberType = memberType;
  }

}
