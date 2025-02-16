package org.doubleone.domain.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ManagerProfileUpdateRequestDto {
    private Long memberId;
    private String profileImg;
    private String phoneNum;
    private String address;
    private String password;
    private String passwordConfirm;
}
