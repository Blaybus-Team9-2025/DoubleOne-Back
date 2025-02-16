package org.doubleone.domain.condition.repository;

import org.doubleone.domain.condition.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
    Optional<Condition> findById(Long id);
}
