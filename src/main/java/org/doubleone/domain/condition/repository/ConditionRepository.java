package org.doubleone.domain.condition.repository;

import java.util.Collection;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Long> {

    // 최신순 조회
    List<Condition> findAllByOrderByCreatedAtDesc();

    // 매칭 미완료 조회
    @Query("SELECT c FROM Condition c WHERE c.conditionId NOT IN (SELECT m.condition.conditionId FROM Matching m)")
    List<Condition> findUnmatchedConditions();

    @Query("SELECT c FROM Condition c WHERE c.senior.manager = :manager")
    List<Condition> findByManager(Manager manager);
}
