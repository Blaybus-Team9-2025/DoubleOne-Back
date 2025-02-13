package org.doubleone.domain.worker.repository;

import java.util.List;

import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRegionRepository extends JpaRepository<WorkerRegion, Long> {

    List<WorkerRegion> findByWorker(Worker worker);

<<<<<<< HEAD
}
=======
}
>>>>>>> 67fa96a (변경사항저장)
