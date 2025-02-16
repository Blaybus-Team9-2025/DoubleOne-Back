package org.doubleone.domain.worker.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkerUpdateRequest {
    String profileImg;
    String phoneNum;
    String address;
    boolean hasVehicle;
    boolean hasTrained;
    String license;
}