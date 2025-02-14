package org.doubleone.domain.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerUpdateRequestDto {

    // 개인정보 수정
    private String profileImg;       // 프로필 사진 URL
    private String phoneNum;         // 연락처
    private String address;          // 주소
    private String password;         // 비밀번호
    private String passwordConfirm;  // 비밀번호 확인

    // 센터정보 수정
    private Boolean hasTruck;        // 목욕 차량 소유 여부 (예/아니오)
    private String centerGrade;      // 센터 등급
    private String centerPeriod;     // 영업 기간
    private String centerInfo;       // 기타 센터 정보
    private String centerMessage;    // 센터 한마디 (500자 제한)

}
