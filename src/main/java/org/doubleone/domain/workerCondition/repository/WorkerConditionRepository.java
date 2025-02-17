package org.doubleone.domain.workerCondition.repository;

import java.util.List;
import java.util.Optional;

import io.lettuce.core.dynamic.annotation.Param;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkerConditionRepository extends JpaRepository<WorkerCondition, Long> {
    List<WorkerCondition> findByWorker(Worker worker);
    Optional<WorkerCondition> findById(Long id);

    @Query("SELECT wc FROM WorkerCondition wc " +
            "JOIN wc.workerRegions wr " +
            "JOIN wr.region r " +
            "JOIN wc.workerSchedules ws " +
            "JOIN ws.schedule wsch " +
            "LEFT JOIN SeniorSchedule ss ON wsch.day = ss.schedule.day " +
            "LEFT JOIN ss.schedule sschd " +
            "WHERE wc.hasTrained = true " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN r.district = :district AND r.neighborhood = :neighborhood THEN 1 " +
            "       WHEN r.district = :district AND r.neighborhood <> :neighborhood THEN 2 " +
            "       WHEN r.district = :district AND " +
            "            (FUNCTION('STR_TO_DATE', wsch.startTime, '%H:%i:%s') > FUNCTION('STR_TO_DATE', sschd.endTime, '%H:%i:%s') OR " +
            "             FUNCTION('STR_TO_DATE', wsch.endTime, '%H:%i:%s') < FUNCTION('STR_TO_DATE', sschd.startTime, '%H:%i:%s')) THEN 3 " +
            "       ELSE 4 " +
            "   END, wc.workerConditionId ASC")
    List<WorkerCondition> findWorkerByMatchingSchedule(
            @Param("neighborhood") String neighborhood,
            @Param("district") String district);


}
