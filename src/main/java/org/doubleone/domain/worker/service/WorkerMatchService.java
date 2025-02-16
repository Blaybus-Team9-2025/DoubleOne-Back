package org.doubleone.domain.worker.service;

import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.domain.worker.dto.response.WorkerMatchResponseDto;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
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

    public List<WorkerMatchResponseDto> findWorkersBySenior(Long seniorId) {
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Senior not found"));

        List<WorkerCondition> workerConditions = workerService.getMatchedWorkerBySenior(senior);
        return workerConditions.stream().map(workerCondition -> {
            Worker worker = workerCondition.getWorker();
            String workerName = (worker != null) ? worker.getName() : "Unknown";

            List<WorkerRegionDto> regionDtos = (workerCondition.getWorkerRegions() != null) ? workerCondition.getWorkerRegions().stream()
                    .map(region -> WorkerRegionDto.builder()
                            .city(region.getRegion().getCity())
                            .district(region.getRegion().getDistrict())
                            .neighborhood(region.getRegion().getNeighborhood())
                            .build()
                    ).collect(Collectors.toList()) : Collections.emptyList();

            String organization = (workerCondition.getWorkPeriods() != null && !workerCondition.getWorkPeriods().isEmpty()) ?
                    workerCondition.getWorkPeriods().get(0).getOrganization() : null;

            return WorkerMatchResponseDto.builder()
                    .workerId(worker != null ? worker.getWorkerId() : null)
                    .workerName(workerName)
                    .workerRegions(regionDtos)
                    .organization(organization)
                    .seniorAddress(senior.getAddress())
                    .seniorName(senior.getName())
                    .build();
        }).collect(Collectors.toList());
    }
}