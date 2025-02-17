package org.doubleone.domain.endMatching.repository;

import java.util.List;
import org.doubleone.domain.endMatching.entity.EndMatching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EndmatchingRepository extends JpaRepository<EndMatching, Long> {

  @Query("SELECT e FROM EndMatching e " +
      "JOIN e.matching m " +
      "JOIN m.workerCondition c " +
      "JOIN c.worker w " +
      "WHERE w.workerId = :workerId")
  List<EndMatching> findByWorkerId(@Param("workerId") Long workerId);
}
