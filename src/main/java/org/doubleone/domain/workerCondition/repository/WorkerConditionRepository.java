package org.doubleone.domain.workerCondition.repository;

import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerConditionRepository extends JpaRepository<WorkerCondition, Long> {

}
