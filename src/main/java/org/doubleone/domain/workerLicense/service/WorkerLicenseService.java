package org.doubleone.domain.workerLicense.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.license.entity.License;
import org.doubleone.domain.license.repository.LicenseRepository;
import org.doubleone.domain.worker.dto.response.WorkerLicenseDto;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.doubleone.domain.workerLicense.repository.WorkerLicenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerLicenseService {

  private final WorkerLicenseRepository workerLicenseRepository;
  private final LicenseRepository licenseRepository;

  public void createWorkerLicense(WorkerCondition workerCondition, WorkerLicenseDto licenseDto) {
    License license = licenseRepository.findByLicenseTypeAndLicenseNum(
        licenseDto.licenseType(), licenseDto.licenseNum()
    ).orElseGet(() -> {
      License newLicense = License.builder()
          .licenseType(licenseDto.licenseType())
          .licenseNum(licenseDto.licenseNum())
          .build();
      return licenseRepository.save(newLicense);
    });
    WorkerLicense workerLicense = licenseDto.toEntity(workerCondition, license);
    workerLicenseRepository.save(workerLicense);
  }

  public void update(WorkerCondition workerCondition, WorkerLicenseDto licenseDto) {
    // licenseDto에 해당하는 license 존재하는지 조회
    License license = licenseRepository.findByLicenseTypeAndLicenseNum(
        licenseDto.licenseType(), licenseDto.licenseNum()
    ).orElseGet(() -> {
      License newLicense = License.builder()
          .licenseType(licenseDto.licenseType())
          .licenseNum(licenseDto.licenseNum())
          .build();
      return licenseRepository.save(newLicense);
    });

    Optional<WorkerLicense> existingWorkerLicense = workerLicenseRepository.findByWorkerConditionAndLicense(
        workerCondition, license
    );

    if (existingWorkerLicense.isPresent()) {
      WorkerLicense workerLicense = existingWorkerLicense.get();
      workerLicense.updateLicense(license);
    } else {
      WorkerLicense newWorkerLicense = WorkerLicense.builder()
          .workerCondition(workerCondition)
          .license(license)
          .build();
      workerLicenseRepository.save(newWorkerLicense);
    }
  }


}
