package org.doubleone.domain.workerSchedule.repository;

import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerSchedule.entity.SeniorSchedule;
import org.doubleone.domain.workerSchedule.entity.WorkerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeniorScheduleRepository extends JpaRepository<SeniorSchedule, Long> {
    Optional<SeniorSchedule> findByConditionAndSchedule(Condition condition, Schedule schedule);

}
