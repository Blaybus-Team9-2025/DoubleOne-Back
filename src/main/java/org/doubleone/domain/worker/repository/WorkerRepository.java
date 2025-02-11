package org.doubleone.domain.worker.repository;

import org.doubleone.domain.worker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
