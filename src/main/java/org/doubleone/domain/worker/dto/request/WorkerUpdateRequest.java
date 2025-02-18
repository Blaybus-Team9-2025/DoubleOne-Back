package org.doubleone.domain.worker.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public record WorkerUpdateRequest(
    Long workerId,
    MultipartFile imgFile,
    String phoneNum,
    String address,
    String detailAddress,
    String zipcode,
    boolean hasVehicle,
    boolean hasTrained,
    // 비밀번호 변경
    String password,
    String passwordConfirm) {
}