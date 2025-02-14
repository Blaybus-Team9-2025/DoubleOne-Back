package org.doubleone.domain.workerRegion.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.region.repository.RegionRepository;
import org.doubleone.domain.region.entity.Region;
import org.doubleone.domain.region.entity.Region;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.worker.dto.response.WorkerRegionDto;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerRegion.repository.WorkerRegionRepository;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.doubleone.domain.workerRegion.entity.WorkerRegion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerRegionService {
  private final WorkerRegionRepository workerRegionRepository;
  private final RegionRepository regionRepository;

  public void createWorkerRegion(WorkerCondition workerCondition, WorkerRegionDto regionDto) {
    Region region = regionRepository.findByCityAndDistrictAndNeighborhood(
        regionDto.city(), regionDto.district(), regionDto.neighborhood()
    ).orElseGet(() -> {
      Region newRegion = Region.builder()
          .city(regionDto.city())
          .district(regionDto.district())
          .neighborhood(regionDto.neighborhood())
          .build();
      return regionRepository.save(newRegion);
    });
    WorkerRegion workerRegion = regionDto.toEntity(workerCondition, region);
    workerRegionRepository.save(workerRegion);
  }

  public void update(WorkerCondition workerCondition, WorkerRegionDto regionDto) {
    // regionDto에 해당하는 region 존재하는지 조회
    Region region = regionRepository.findByCityAndDistrictAndNeighborhood(
        regionDto.city(), regionDto.district(), regionDto.neighborhood()
    ).orElseGet(() -> {
      Region newRegion = Region.builder()
          .city(regionDto.city())
          .district(regionDto.district())
          .neighborhood(regionDto.neighborhood())
          .build();
      return regionRepository.save(newRegion);
    });

    Optional<WorkerRegion> existingWorkerRegion = workerRegionRepository.findByWorkerConditionAndRegion(
        workerCondition, region
    );

    if (existingWorkerRegion.isPresent()) {
      WorkerRegion workerRegion = existingWorkerRegion.get();
      workerRegion.updateRegion(region);
    } else {
      WorkerRegion newWorkerRegion = WorkerRegion.builder()
          .workerCondition(workerCondition)
          .region(region)
          .build();
      workerRegionRepository.save(newWorkerRegion);
    }
  }
}
