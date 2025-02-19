package org.doubleone.domain.matching.repository;

import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.entity.RunningStatus;
//import org.doubleone.domain.worker.dto.response.WorkerMatchingAlarmUnitDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    List<Matching> findByMatchingStatus(MatchingStatus matchingStatus);
    @Query("SELECT COUNT(DISTINCT m.condition.senior) FROM Matching m WHERE m.condition.senior.manager = :manager")
    int countByManager(@Param("manager") Manager manager);

    @Query("SELECT COUNT(DISTINCT m.condition.senior) FROM Matching m WHERE m.condition.senior.manager = :manager AND m.matchingStatus = :status")
    int countByManagerAndMatchingStatus(@Param("manager") Manager manager, @Param("status") MatchingStatus status);

    @Query("SELECT COUNT(DISTINCT m.condition.senior) FROM Matching m WHERE m.condition.senior.manager = :manager AND m.runningStatus = :status")
    int countByManagerAndRunningStatus(@Param("manager") Manager manager, @Param("status") RunningStatus status);


    //@Query("SELECT m FROM Matching m WHERE m.workerCondition.worker = :worker AND m.matchingStatus = :matchingStatus")
    //List<WorkerMatchingAlarmUnitDto> findByWorkerAndRunningStatus(Worker worker, MatchingStatus matchingStatus);

    @Query("SELECT m FROM Matching m WHERE m.workerCondition.worker = :worker AND m.matchingStatus = :matchingStatus")
    List<Matching> findByWorkerAndRunningStatus(Worker worker, MatchingStatus matchingStatus);

}
