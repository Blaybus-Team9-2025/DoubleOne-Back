package org.doubleone.domain.manager.dto;

import java.nio.channels.MulticastChannel;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.member.entity.Gender;
import org.springframework.web.multipart.MultipartFile;

public record ManagerUpdateRequestDto(
    Long memberId,      // 로그인한 사용자 ID
    MultipartFile imgFile,
    String phoneNum,
    String address,
    String detailAddress,
    String zipcode,
    String password,
    String passwordConfirm){
}
