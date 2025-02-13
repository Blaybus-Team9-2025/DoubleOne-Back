package org.doubleone.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id", updatable = false)
  private Long memberId;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "member_type")
  @NotNull
  @Enumerated(EnumType.STRING)
  private MemberType memberType;

  @Column(name = "member_status")
  @NotNull
  @Enumerated(EnumType.STRING)
  private MemberStatus memberstatus;

  @Builder
  public Member(String email, String password) {
    this.email = email;
    this.password = password;
    this.memberstatus = MemberStatus.ACTIVE;
    this.memberType = MemberType.UNKNOWN;
  }



}
