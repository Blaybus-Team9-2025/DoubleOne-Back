package org.doubleone.domain.worker.repository;

import java.util.List;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerConditionRepository extends JpaRepository<WorkerCondition, Long> {
    List<WorkerCondition> findByWorkerId(Long workerId);
}
