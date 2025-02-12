package org.doubleone.domain.worker.repository;

import java.util.List;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRegionRepository extends JpaRepository<WorkerRegion, Long> {
    List<WorkerRegion> findByWorkerId(Long workerId);
}
