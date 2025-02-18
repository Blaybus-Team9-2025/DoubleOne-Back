package org.doubleone.domain.condition.repository;

import org.doubleone.domain.condition.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Long> {

    // 최신순 조회
    List<Condition> findAllByOrderByCreatedAtDesc();

    // 매칭 미완료 조회
    @Query("SELECT c FROM Condition c WHERE c.seniorConditionId NOT IN (SELECT m.condition.seniorConditionId FROM Matching m)")
    List<Condition> findUnmatchedConditions();

}
