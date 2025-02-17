package org.doubleone.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberProfileUpdateRequestDto {
    private String profileImg;
    private String phoneNum;
    private String address;
    private String password;
    private String passwordConfirm;
}
