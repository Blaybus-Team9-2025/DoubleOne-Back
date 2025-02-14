package org.doubleone.domain.condition.repository;

import org.doubleone.domain.condition.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
}
