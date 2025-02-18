package org.doubleone.domain.senior.repository;

import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.senior.entity.Senior;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeniorRepository extends JpaRepository<Senior, Long> {

  int countByManager(Manager manager);
}
