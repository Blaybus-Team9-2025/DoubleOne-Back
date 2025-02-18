package org.doubleone.domain.senior.repository;

import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.senior.entity.Senior;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeniorRepository extends JpaRepository<Senior, Long> {


    // 최신순 조회
    List<Senior> findAllByOrderByCreatedAtDesc();

    // 매칭 미완료순 조회 (BEFORE_REQUEST 상태만 조회)
    List<Senior> findByMatchingStatusOrderByCreatedAtDesc(MatchingStatus matchingStatus);

  int countByManager(Manager manager);

}
