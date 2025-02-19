package org.doubleone.domain.manager.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.member.entity.Gender;
import org.doubleone.domain.member.entity.Member;

@Builder
public record ManagerDetailsResponseDto(
    // manager
    String profileImg,
    String name,
    String email,
    Gender gender,
    LocalDate birthDate,
    String phoneNum,
    // center
    String centerImg,
    String centerName,
    String address,
    String zipcode,
    String detailAddress,
    boolean hasTruck,
    String centerGrade,
    String centerPeriod,
    String centerInfo,
    String centerMessage
) {

  public static ManagerDetailsResponseDto from(Manager manager){
    return ManagerDetailsResponseDto.builder()
        .profileImg(manager.getProfileImg())
        .name(manager.getName())
        .email(manager.getMember().getEmail())
        .gender(manager.getGender())
        .birthDate(manager.getBirthDate())
        .phoneNum(manager.getPhoneNum())
        .centerImg(manager.getCenterImg())
        .centerName(manager.getCenterName())
        .address(manager.getAddress())
        .zipcode(manager.getZipcode())
        .detailAddress(manager.getDetailAddress())
        .hasTruck(manager.isHasTruck())
        .centerGrade(manager.getCenterGrade())
        .centerPeriod(manager.getCenterPeriod())
        .centerInfo(manager.getCenterInfo())
        .centerMessage(manager.getCenterMessage())
        .build();
  }

}
