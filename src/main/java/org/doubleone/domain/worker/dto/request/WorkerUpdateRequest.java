package org.doubleone.domain.worker.dto.request;

import lombok.Getter;

@Getter
public class WorkerUpdateRequest {
    private String phoneNum;
    private String address;
    private boolean hasTrained;
    private boolean hasVehicle;
    private String license;
}