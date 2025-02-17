package org.doubleone.domain.condition.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
}
