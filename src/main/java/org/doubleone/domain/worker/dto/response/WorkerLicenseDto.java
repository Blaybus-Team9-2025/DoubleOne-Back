package org.doubleone.domain.worker.dto.response;

import lombok.Builder;
import org.doubleone.domain.license.entity.License;
import org.doubleone.domain.license.entity.LicenseType;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;

@Builder
public record WorkerLicenseDto(
    LicenseType licenseType,
    String licenseNum
) {

  public WorkerLicense toEntity(WorkerCondition workerCondition, License license){
    return WorkerLicense.builder()
        .workerCondition(workerCondition)
        .license(license)
        .build();
  }

  public static WorkerLicenseDto from(WorkerLicense workerLicense){
    return WorkerLicenseDto.builder()
        .licenseType(workerLicense.getLicense().getLicenseType())
        .licenseNum(workerLicense.getLicense().getLicenseNum())
        .build();
  }

}
