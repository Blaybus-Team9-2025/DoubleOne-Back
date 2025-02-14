package org.doubleone.domain.worker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.workerCondition.repository.WorkerConditionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {
  private final WorkerRepository workerRepository;
  private final WorkerConditionRepository workerConditionRepository;


  public List<Long> getWorkerIdsBySeniorAddress(Senior senior) {
    String[] addressParts = senior.getAddress().split(" ");
    String district = addressParts[addressParts.length - 2]; // "강남구"
    String neighborhood = addressParts[addressParts.length - 1]; // "역삼동"

    return workerConditionRepository.findWorkerIdsByMatchingSchedule(neighborhood, district);
  }


}
