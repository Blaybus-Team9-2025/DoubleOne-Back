package org.doubleone.domain.worker.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;

@Getter
public class WorkerUpdateRequest {
    private String phoneNum;
    private String address;
    private boolean hasTrained;
    private boolean hasVehicle;
    private String license;
}