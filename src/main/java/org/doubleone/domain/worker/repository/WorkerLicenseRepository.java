package org.doubleone.domain.worker.repository;

import java.util.List;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerLicense.entity.WorkerLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerLicenseRepository extends JpaRepository<WorkerLicense, Long> {
    List<WorkerLicense> findByWorker(Worker worker);
}
