package org.doubleone.domain.workerRegion.repository;

import java.util.Optional;
import org.doubleone.domain.region.entity.Region;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRegionRepository extends JpaRepository<WorkerRegion, Long> {

  Optional<WorkerRegion> findByWorkerConditionAndRegion(WorkerCondition workerCondition, Region region);
}
