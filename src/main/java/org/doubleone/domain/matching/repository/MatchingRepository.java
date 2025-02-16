package org.doubleone.domain.matching.repository;

import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    List<Matching> findByMatchingStatus(MatchingStatus matchingStatus);
}
