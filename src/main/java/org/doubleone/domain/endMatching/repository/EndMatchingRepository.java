package org.doubleone.domain.endMatching.repository;

import java.util.List;
import java.util.Optional;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.endMatching.entity.EndMatching;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EndMatchingRepository extends JpaRepository<EndMatching, Long> {

  @Query("SELECT e FROM EndMatching e " +
      "JOIN e.matching m " +
      "JOIN m.workerCondition c " +
      "JOIN c.worker w " +
      "WHERE w.workerId = :workerId")
  List<EndMatching> findByWorkerId(@Param("workerId") Long workerId);

  Optional<EndMatching> findByMatching(Matching matching);

  @Query("SELECT COUNT(DISTINCT e.matching.condition.senior) FROM EndMatching e WHERE e.matching.condition.senior.manager = :manager")
  int countByManager(@Param("manager") Manager manager);

  @Query("SELECT CASE WHEN COUNT(em) > 0 THEN true ELSE false END " +
      "FROM EndMatching em " +
      "WHERE em.matching.condition = :condition")
  boolean existsByCondition(@Param("condition") Condition condition);

}
