package org.doubleone.domain.workerCondition.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkerConditionRepository extends JpaRepository<WorkerCondition, Long> {
//    @Query("SELECT wc.worker.workerId FROM WorkerCondition wc " +
//            "JOIN wc.workerRegions wr " +
//            "JOIN wr.region r " +
//            "WHERE r.neighborhood = :neighborhood AND r.district = :district " +
//            "ORDER BY 1 " +
//            "UNION ALL " +
//            "SELECT wc.worker.workerId FROM WorkerCondition wc " +
//            "JOIN wc.workerRegions wr " +
//            "JOIN wr.region r " +
//            "WHERE r.district = :district AND r.neighborhood <> :neighborhood " +
//            "ORDER BY 1")
//    List<Long> findWorkerIdsByAddress(@Param("neighborhood") String neighborhood,
//                                       @Param("district") String district);
//
    Optional<WorkerCondition> findById(Long id);

    @Query("SELECT DISTINCT wc.worker.workerId FROM WorkerCondition wc " +
            "JOIN wc.workerRegions wr " +
            "JOIN wr.region r " +
            "JOIN wc.workerSchedules ws " +
            "JOIN ws.schedule wsch " +
            "JOIN SeniorSchedule ss ON wsch.day = ss.schedule.day " +  // 요일 일치
            "JOIN ss.condition sc " +
            "JOIN sc.seniorSchedules ssch " +
            "JOIN ssch.schedule sschd " +
            "WHERE (r.district = :district AND r.neighborhood = :neighborhood) " + // 1순위 지역 조건
            "   OR (r.district = :district AND r.neighborhood <> :neighborhood) " + // 2순위 지역 조건
            "   AND ((wsch.startTime <= sschd.endTime AND wsch.endTime >= sschd.startTime)) " + // 2순위 근무시간 조건
            "ORDER BY " +
            "   CASE " +
            "       WHEN r.district = :district AND r.neighborhood = :neighborhood THEN 1 " + // 1순위: 지역 일치
            "       WHEN r.district = :district THEN 2 " + // 2순위: 같은 구지만 다른 동
            "       ELSE 3 " +
            "   END")
    List<Long> findWorkerIdsByMatchingSchedule(
            @Param("neighborhood") String neighborhood,
            @Param("district") String district);

}
