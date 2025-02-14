package org.doubleone.domain.workerCondition.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkerConditionRepository extends JpaRepository<WorkerCondition, Long> {
    @Query("SELECT wc.worker.workerId FROM WorkerCondition wc " +
            "JOIN wc.workerRegions wr " +
            "JOIN wr.region r " +
            "WHERE r.neighborhood = :neighborhood AND r.district = :district " +
            "ORDER BY 1 " +
            "UNION ALL " +
            "SELECT wc.worker.workerId FROM WorkerCondition wc " +
            "JOIN wc.workerRegions wr " +
            "JOIN wr.region r " +
            "WHERE r.district = :district AND r.neighborhood <> :neighborhood " +
            "ORDER BY 1")
    List<Long> findWorkerIdsByAddress(@Param("neighborhood") String neighborhood,
                                       @Param("district") String district);

//    @Query("SELECT wc.worker.workerId FROM WorkerCondition wc " +
//            "JOIN wc.workerRegions wr " +
//            "JOIN wr.region r " +
//            "WHERE r.neighborhood = :neighborhood " +
//            "OR (r.district = :district AND NOT EXISTS " +
//            "(SELECT 1 FROM WorkerCondition wc2 " +
//            "JOIN wc2.workerRegions wr2 " +
//            "JOIN wr2.region r2 " +
//            "WHERE r2.neighborhood = :neighborhood))")
//    List<Long> findWorkerIdsByAddress(@Param("neighborhood") String neighborhood,
//                                                     @Param("district") String district);
}
