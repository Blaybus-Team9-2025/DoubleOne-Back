package org.doubleone.domain.senior.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.schedule.entity.Schedule;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "senior_schedule")
@Getter
@Setter
@Log4j2
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeniorSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "senior_schedule_id", updatable = false)
    private Long seniorScheduleId;

    //senior_condition
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_condition_id", updatable = false)
    @NotNull
    @JsonIgnore
    private Condition condition;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", updatable = false)
    @NotNull
    @JsonIgnore
    private Schedule schedule;

    public void updateSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}