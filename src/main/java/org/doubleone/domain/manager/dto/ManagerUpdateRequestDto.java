package org.doubleone.domain.manager.dto;

import java.nio.channels.MulticastChannel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ManagerUpdateRequestDto {

    private Long memberId;           // 로그인한 사용자 ID

    // 개인정보 수정
    private String phoneNum;
    private String address;
    private String password;
    private String passwordConfirm;
}
