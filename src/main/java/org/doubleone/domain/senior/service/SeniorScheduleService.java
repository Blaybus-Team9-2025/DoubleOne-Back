package org.doubleone.domain.senior.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.schedule.repository.ScheduleRepository;
import org.doubleone.domain.senior.dto.SeniorScheduleDto;
import org.doubleone.domain.senior.entity.SeniorSchedule;
import org.doubleone.domain.senior.repository.SeniorScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SeniorScheduleService {
    private final SeniorScheduleRepository seniorScheduleRepository;
    private final ScheduleRepository scheduleRepository;

    public void createSeniorSchedule(Condition condition, SeniorScheduleDto scheduleDto)
    {
        Schedule schedule = scheduleRepository.findByDayAndStartTimeAndEndTime(
                scheduleDto.day(), scheduleDto.startTime(), scheduleDto.endTime()
        ).orElseGet(() -> {
            Schedule newSchedule = Schedule.builder()
                    .day(scheduleDto.day())
                    .startTime(scheduleDto.startTime())
                    .endTime(scheduleDto.endTime())
                    .build();
            return scheduleRepository.save(newSchedule);
        });
        SeniorSchedule seniorSchedule = scheduleDto.toEntity(condition, schedule);
        seniorScheduleRepository.save(seniorSchedule);
    }

    public void update(Condition condition, SeniorScheduleDto scheduleDto) {
        // scheduleDto에 해당하는 schedule 존재하는지 조회
        Schedule schedule = scheduleRepository.findByDayAndStartTimeAndEndTime(
                scheduleDto.day(), scheduleDto.startTime(), scheduleDto.endTime()
        ).orElseGet(() -> {
            Schedule newSchedule = Schedule.builder()
                    .day(scheduleDto.day())
                    .startTime(scheduleDto.startTime())
                    .endTime(scheduleDto.endTime())
                    .build();
            return scheduleRepository.save(newSchedule);
        });

        Optional<SeniorSchedule> existingSeniorSchedule = seniorScheduleRepository.findByConditionAndSchedule(
                condition, schedule
        );

        if (existingSeniorSchedule.isPresent()) {
            SeniorSchedule seniorSchedule = existingSeniorSchedule.get();
            seniorSchedule.updateSchedule(schedule);
        } else {
            SeniorSchedule newSeniorSchedule = SeniorSchedule.builder()
                    .condition(condition)
                    .schedule(schedule)
                    .build();
            seniorScheduleRepository.save(newSeniorSchedule);
        }
    }

}