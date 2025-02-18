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
            "LEFT JOIN wc.workerSchedules ws " +
            "LEFT JOIN ws.schedule wsch " +
            "LEFT JOIN Condition sc ON sc = :seniorCondition " +
            "LEFT JOIN sc.seniorSchedules ss " +
            "LEFT JOIN ss.schedule ssch " +
            "WHERE (r.city = :city OR r.district = :district OR r.neighborhood = :neighborhood ) " +
            "AND wc.hasTrained = :hasDementiaSymptoms " + //치매환자일때 요양사의 치매교육 이수여부 확인
            "AND w.gender = :prefer " + //선호하는 성별 반영
            "ORDER BY " +
            "CASE " +
            " WHEN r.neighborhood = :neighborhood " +
            "      AND (r.district <> :district AND r.city <> :city) " +
            "      AND (wsch.day = ssch.day AND (wsch.startTime < ssch.endTime AND wsch.endTime > ssch.startTime)) THEN 1 " +
            " WHEN r.district = :district " +
            "      AND r.neighborhood <> :neighborhood " +
            "      AND (wsch.day = ssch.day AND (wsch.startTime < ssch.endTime AND wsch.endTime > ssch.startTime)) THEN 2 " +
            " WHEN r.district = :district " +
            "      AND r.neighborhood <> :neighborhood " +
            "      AND wc.discuss = true " +
            "      AND NOT (wsch.day = ssch.day AND (wsch.startTime < ssch.endTime AND wsch.endTime > ssch.startTime)) THEN 3 " +
            " WHEN r.city = :city AND wc.hasVehicle = true THEN 4 " +
            " ELSE 5 " +  // 나머지
            "END, wc.workerConditionId ASC")
    List<WorkerCondition> findWorkerByMatchingSchedule(
            @Param("neighborhood") String neighborhood,
            @Param("district") String district,
            @Param("city") String city,
            @Param("prefer") Gender prefer,
            @Param("hasDementiaSymptoms") boolean hasDementiaSymptoms,
            @Param("seniorCondition") Condition seniorCondition
            );
}
