package org.doubleone.domain.worker.dto.response;

import org.doubleone.domain.license.entity.License;
import org.doubleone.domain.license.entity.LicenseType;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;

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

}
