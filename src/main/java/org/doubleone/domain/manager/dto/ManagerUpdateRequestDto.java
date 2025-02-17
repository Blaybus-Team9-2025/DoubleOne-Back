package org.doubleone.domain.manager.dto;

import java.nio.channels.MulticastChannel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public record ManagerUpdateRequestDto(
    Long memberId,      // 로그인한 사용자 ID
    MultipartFile imgFile,
    String phoneNum,
    String address,
    String password,
    String passwordConfirm){
}
