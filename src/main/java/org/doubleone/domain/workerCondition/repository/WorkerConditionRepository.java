package org.doubleone.domain.workerCondition.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkerConditionRepository extends JpaRepository<WorkerCondition, Long> {
//
    Optional<WorkerCondition> findById(Long id);

    @Query("SELECT DISTINCT wc.worker.workerId FROM WorkerCondition wc " +
            "JOIN wc.workerRegions wr " +
            "JOIN wr.region r " +
            "JOIN wc.workerSchedules ws " +
            "JOIN ws.schedule wsch " +
            "JOIN SeniorSchedule ss ON wsch.day = ss.schedule.day " +
            "JOIN ss.condition sc " +
            "JOIN sc.seniorSchedules ssch " +
            "JOIN ssch.schedule sschd " +
            "WHERE (r.district = :district AND r.neighborhood = :neighborhood) " + // 1순위: 같은 동
            "   OR (r.district = :district AND r.neighborhood <> :neighborhood) " + // 2순위: 같은 구, 다른 동
            "   OR ((r.district <> :district) AND " + // 3순위: 지역 다르지만
            "       (wsch.startTime <= sschd.endTime AND wsch.endTime >= sschd.startTime)) " + // 시간 겹치는 경우만
            "ORDER BY " +
            "   CASE " +
            "       WHEN r.district = :district AND r.neighborhood = :neighborhood THEN 1 " + // 1순위: 같은 동
            "       WHEN r.district = :district THEN 2 " + // 2순위: 같은 구, 다른 동
            "       WHEN r.district <> :district AND " +
            "            (wsch.startTime <= sschd.endTime AND wsch.endTime >= sschd.startTime) THEN 3 " + // 3순위: 지역 다르지만 시간 겹침
            "       ELSE 4 " +
            "   END")
    List<Long> findWorkerIdsByMatchingSchedule(
            @Param("neighborhood") String neighborhood,
            @Param("district") String district);


}
