package org.doubleone.domain.matching.repository;

import java.time.LocalDate;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.entity.RunningStatus;
//import org.doubleone.domain.worker.dto.response.WorkerMatchingAlarmUnitDto;
import org.doubleone.domain.worker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    List<Matching> findByMatchingStatus(MatchingStatus matchingStatus);

//    SELECT COUNT(DISTINCT m.condition.senior) 제거
    @Query("SELECT COUNT(m) FROM Matching m WHERE m.condition.senior.manager = :manager")
    int countByManager(@Param("manager") Manager manager);

    @Query("SELECT COUNT(m) FROM Matching m WHERE m.condition.senior.manager = :manager AND m.matchingStatus = :status")
    int countByManagerAndMatchingStatus(@Param("manager") Manager manager, @Param("status") MatchingStatus status);

    @Query("SELECT COUNT(m) FROM Matching m WHERE m.condition.senior.manager = :manager AND m.runningStatus = :status")
    int countByManagerAndRunningStatus(@Param("manager") Manager manager, @Param("status") RunningStatus status);

    @Query("SELECT m FROM Matching m WHERE m.workerCondition.worker = :worker AND m.matchingStatus = :matchingStatus")
    List<Matching> findByWorkerAndRunningStatus(Worker worker, MatchingStatus matchingStatus);

    @Query("SELECT COUNT(m) FROM Matching m WHERE m.condition.senior.manager = :manager AND m.createdAt BETWEEN :startDate AND :endDate")
    int countByManagerAndDate(Manager manager, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(m) FROM Matching m WHERE m.condition.senior.manager = :manager AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    int countByManagerAndMonth(@Param("manager") Manager manager, @Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT AVG(matchCount) FROM ("
        + "SELECT COUNT(*) AS matchCount "
        + "FROM matching m "
        + "WHERE m.condition.senior.manager = :manager "
        + "GROUP BY m.condition.senior.senior_id) AS subquery", nativeQuery = true)
    double avgMatchesPerSenior(@Param("manager") Manager manager);
}
