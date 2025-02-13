package org.doubleone.domain.schedule.repository;

import java.util.Optional;
import org.doubleone.domain.schedule.entity.Day;
import org.doubleone.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  Optional<Schedule> findByDayAndStartTimeAndEndTime(Day day, String s, String s1);
}
