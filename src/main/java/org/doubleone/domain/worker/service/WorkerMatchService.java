package org.doubleone.domain.worker.service;

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

    public WorkerMatchService(WorkerService workerService, SeniorRepository seniorRepository) {
        this.workerService = workerService;
        this.seniorRepository = seniorRepository;
    }

    public WorkerMatchResponseDto findWorkersBySenior(Long seniorId) {
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));

        List<WorkerCondition> workerConditions = workerService.getMatchedWorkerBySenior(senior);
        List<WorkerDetailDto> workers = workerConditions.stream().map(workerCondition -> {
            Worker worker = workerCondition.getWorker();
            List<WorkerRegionDto> regionDtos = (workerCondition.getWorkerRegions() != null) ? workerCondition.getWorkerRegions().stream()
                    .map(region -> WorkerRegionDto.builder()
                            .city(region.getRegion().getCity())
                            .district(region.getRegion().getCity())
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

            return WorkerDetailDto.builder()
                    .workerId(worker.getWorkerId())
                    .workerName(worker.getName())
                    .workerRegions(regionDtos)
                    .workPeriods(workPeriods)
                    .build();
        }).collect(Collectors.toList());

        return WorkerMatchResponseDto.builder()
                .seniorName(senior.getName())
                .seniorAddress(senior.getAddress())
                .workers(workers)
                .build();
    }
}