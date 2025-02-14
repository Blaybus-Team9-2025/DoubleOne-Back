package org.doubleone.domain.workerLicense.entity;

import java.util.List;
import org.doubleone.domain.worker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerLicenseRepository extends JpaRepository<WorkerLicense, Long> {

    List<WorkerLicense> findByWorker(Worker worker);

}
