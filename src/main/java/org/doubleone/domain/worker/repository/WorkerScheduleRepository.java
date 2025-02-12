package org.doubleone.domain.worker.repository;

import java.util.List;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Long> {
    List<WorkerSchedule> findByWorkerId(Long workerId);
}
