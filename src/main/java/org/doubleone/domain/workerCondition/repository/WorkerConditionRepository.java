package org.doubleone.domain.workerCondition.repository;

import java.util.List;
import java.util.Optional;

import io.lettuce.core.dynamic.annotation.Param;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.worker.entity.Gender;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkerConditionRepository extends JpaRepository<WorkerCondition, Long> {
    List<WorkerCondition> findByWorker(Worker worker);
    Optional<WorkerCondition> findById(Long id);

    @Query("SELECT wc FROM WorkerCondition wc " +
            "LEFT JOIN wc.workerRegions wr " +
            "LEFT JOIN wr.region r " +
            "LEFT JOIN wc.worker w " +
            "WHERE (r.district = :district OR r.neighborhood = :neighborhood ) " +
            "AND w.gender = :prefer " +
            "ORDER BY " +
            "CASE " +
            " WHEN r.neighborhood = :neighborhood THEN 1 " +
            " WHEN r.district = :district THEN 2 " +
            " ELSE 3 " +
            "END, wc.workerConditionId ASC")
    List<WorkerCondition> findWorkerByMatchingSchedule(
            @Param("neighborhood") String neighborhood,
            @Param("district") String district,
            @Param("prefer") Gender prefer);


}
