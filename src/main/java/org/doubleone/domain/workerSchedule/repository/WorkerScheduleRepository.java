package org.doubleone.domain.workerSchedule.repository;

import java.util.Optional;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Long> {

  Optional<WorkerSchedule> findByWorkerConditionAndSchedule(WorkerCondition workerCondition, Schedule schedule);
}
