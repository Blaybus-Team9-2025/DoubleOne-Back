package org.doubleone.domain.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerUpdateRequestDto {
    private String profileImg;       // 프로필 사진 URL
    private String phoneNum;         // 연락처
    private String address;          // 주소
    private String password;         // 비밀번호
    private String passwordConfirm;  // 비밀번호 확인
}
