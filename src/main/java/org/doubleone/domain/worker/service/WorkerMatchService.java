package org.doubleone.domain.worker.service;

import java.time.LocalDate;
import java.time.Period;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.repository.ConditionRepository;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.entity.RunningStatus;
import org.doubleone.domain.matching.repository.MatchingRepository;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.domain.worker.dto.WorkPeriodDto;
import org.doubleone.domain.worker.dto.WorkerDetailDto;
import org.doubleone.domain.worker.dto.response.WorkerMatchResponseDto;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WorkerMatchService {

    private final WorkerService workerService;
    private final SeniorRepository seniorRepository;
    private final ConditionRepository conditionRepository;
    private final MatchingRepository matchingRepository;

    public WorkerMatchService(WorkerService workerService, SeniorRepository seniorRepository, ConditionRepository conditionRepository,
        MatchingRepository matchingRepository) {
        this.workerService = workerService;
        this.seniorRepository = seniorRepository;
        this.conditionRepository = conditionRepository;
        this.matchingRepository = matchingRepository;
    }

    public WorkerMatchResponseDto findWorkersBySenior(Long conditionId) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_CONDITION_NOT_FOUND));
        Senior senior = condition.getSenior();

        List<WorkerCondition> workerConditions = workerService.getMatchedWorkerBySenior(senior, condition);
        List<WorkerDetailDto> workers = workerConditions.stream().map(workerCondition -> {
            Worker worker = workerCondition.getWorker();
            List<WorkerRegionDto> regionDtos = (workerCondition.getWorkerRegions() != null) ? workerCondition.getWorkerRegions().stream()
                    .map(region -> WorkerRegionDto.builder()
                            .city(region.getRegion().getCity())
                            .district(region.getRegion().getDistrict())
                            .neighborhood(region.getRegion().getNeighborhood())
                            .build()
                    ).collect(Collectors.toList()) : List.of();

            List<WorkPeriodDto> workPeriods = (workerCondition.getWorkPeriods() != null) ? workerCondition.getWorkPeriods().stream()
                    .map(workPeriod -> new WorkPeriodDto(
                            workPeriod.getTitle(),
                            workPeriod.getOrganization(),
                            workPeriod.isCurrent(),
                            workPeriod.getStartDate(),
                            workPeriod.getEndDate()
                    )).collect(Collectors.toList()) : List.of();

            boolean isRequestMatching = matchingRepository.existsByConditionAndWorkerCondition(condition, workerCondition);

            return WorkerDetailDto.builder()
                    .workerId(worker.getWorkerId())
                    .workerName(worker.getName())
                    .isRequestMatching(isRequestMatching)
                    .workerRegions(regionDtos)
                    .workPeriods(workPeriods)
                    .build();
        }).collect(Collectors.toList());

        return WorkerMatchResponseDto.builder()
                .seniorName(senior.getName())
                .seniorAddress(senior.getAddress())
                .age(calculateAge(senior.getBirthDate()))
                .profileImg(senior.getProfileImg())
                .workers(workers)
                .build();
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}