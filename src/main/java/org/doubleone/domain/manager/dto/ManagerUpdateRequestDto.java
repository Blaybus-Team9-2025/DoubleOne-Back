package org.doubleone.domain.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerUpdateRequestDto {

    private Long memberId;           // 로그인한 사용자 ID

    // 개인정보 수정
    private String profileImg;
    private String phoneNum;
    private String address;
    private String password;
    private String passwordConfirm;

    // 센터정보 수정
    private Boolean hasTruck;
    private String centerGrade;
    private String centerPeriod;
    private String centerInfo;
    private String centerMessage;

}
