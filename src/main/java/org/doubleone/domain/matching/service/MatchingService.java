package org.doubleone.domain.matching.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.repository.ConditionRepository;
import org.doubleone.domain.endMatching.entity.EndMatching;
import org.doubleone.domain.endMatching.repository.EndmatchingRepository;
import org.doubleone.domain.matching.dto.request.MatchingRequestDto;
import org.doubleone.domain.matching.dto.request.MatchingUpdateRequestDto;
import org.doubleone.domain.matching.dto.response.WorkerMatchingUnitDto;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.repository.MatchingRepository;
import org.doubleone.domain.worker.dto.response.WorkerLicenseDto;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.domain.workerCondition.entity.WorkerCondition;
import org.doubleone.domain.workerCondition.repository.WorkerConditionRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MatchingService {

  private final ConditionRepository conditionRepository;
  private final WorkerConditionRepository workerConditionRepository;
  private final MatchingRepository matchingRepository;
  private final WorkerRepository workerRepository;
  private final EndmatchingRepository endmatchingRepository;

  public void createMatchingRequest(MatchingRequestDto requestDto) {
    Condition seniorCondition = conditionRepository.findById(requestDto.conditionId())
        .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_CONDITION_NOT_FOUND));
    WorkerCondition workerCondition = workerConditionRepository.findById(requestDto.workConditionId())
        .orElseThrow(() -> new CustomException(ErrorCode.WORKER_CONDITION_NOT_FOUND));
    Matching matching = requestDto.toEntity(workerCondition, seniorCondition);
    matchingRepository.save(matching);
  }

  public void updateMatching(Long matchingId, MatchingUpdateRequestDto requestDto) {
    Matching matching = matchingRepository.findById(matchingId)
        .orElseThrow(() -> new CustomException(ErrorCode.MATCHING_NOT_FOUND));
    matching.updateStatus(requestDto.matchingStatus(), requestDto.runningStatus());
  }

  public List<WorkerMatchingUnitDto> getMatchingList(Long workerId) {
    workerRepository.findById(workerId)
        .orElseThrow(() -> new CustomException(ErrorCode.MATCHING_NOT_FOUND));
    return endmatchingRepository.findByWorkerId(workerId).stream()
        .map(WorkerMatchingUnitDto::from)
        .collect(Collectors.toList());
  }
}