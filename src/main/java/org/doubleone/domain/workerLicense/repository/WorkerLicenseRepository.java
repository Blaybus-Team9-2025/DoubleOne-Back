package org.doubleone.domain.workerLicense.repository;

import java.util.Optional;
import org.doubleone.domain.license.entity.License;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerLicenseRepository extends JpaRepository<WorkerLicense, Long> {

  Optional<WorkerLicense> findByWorkerConditionAndLicense(WorkerCondition workerCondition, License license);
}
