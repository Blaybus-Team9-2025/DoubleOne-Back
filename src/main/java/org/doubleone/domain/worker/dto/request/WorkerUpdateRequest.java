package org.doubleone.domain.worker.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkerUpdateRequest {
    String profileImg;
    String phoneNum;
    String address;
    boolean hasVehicle;
    boolean hasTrained;
    // 비밀번호 변경
    private String password;
    private String passwordConfirm;
}