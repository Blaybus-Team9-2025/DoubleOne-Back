package org.doubleone.domain.matching.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.repository.ConditionRepository;
import org.doubleone.domain.endMatching.entity.EndMatching;
import org.doubleone.domain.endMatching.repository.EndMatchingRepository;
import org.doubleone.domain.endMatching.service.EndMatchingService;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.matching.dto.request.MatchingRequestDto;
import org.doubleone.domain.matching.dto.request.MatchingUpdateRequestDto;
import org.doubleone.domain.matching.dto.request.WorkerMatchingScheduleRequestDto;
import org.doubleone.domain.matching.dto.response.ManagerMatchingStatResponseDto;
import org.doubleone.domain.matching.dto.response.WorkerMatchingUnitDto;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.entity.RunningStatus;
import org.doubleone.domain.matching.repository.MatchingRepository;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import org.doubleone.domain.schedule.dto.ScheduleDto;
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
  private final EndMatchingRepository endMatchingRepository;
  private final EndMatchingService endMatchingService;
  private final ManagerRepository managerRepository;
  private final MemberRepository memberRepository;

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
    if(requestDto.matchingStatus() == MatchingStatus.COMPLETED && requestDto.runningStatus() == RunningStatus.ACCEPTED) {
        EndMatching endMatching = EndMatching.builder()
            .matching(matching)
            .startDate(LocalDate.now())
            .endDate(null)
            .schedule(null)
            .build();
        endMatchingRepository.save(endMatching);
    } else if(requestDto.matchingStatus() == MatchingStatus.EXPIRED){
        EndMatching endMatching = endMatchingRepository.findByMatching(matching)
            .orElseThrow(() -> new CustomException(ErrorCode.END_MATCHING_NOT_FOUND));
        endMatching.updateEndDate(LocalDate.now());
    }
  }

  @Transactional(readOnly = true)
  public List<WorkerMatchingUnitDto> getMatchingList(Long workerId) {
    workerRepository.findById(workerId)
        .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));
    return endMatchingRepository.findByWorkerId(workerId).stream()
        .map(WorkerMatchingUnitDto::from)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<ScheduleDto> getMatchingSchedule(Long workerId) {
    return endMatchingRepository.findByWorkerId(workerId).stream()
        .filter(endMatching -> endMatching.getSchedule() != null)
        .map(ScheduleDto::from)
        .collect(Collectors.toList());
  }

  public void createMatchingSchedule(Long workerId, WorkerMatchingScheduleRequestDto requestDto) {
    workerRepository.findById(workerId)
        .orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));

    if (requestDto.scheduleDtoList() != null && !requestDto.scheduleDtoList().isEmpty()) {
      requestDto.scheduleDtoList().forEach(scheduleDto ->
          endMatchingService.updateSchedule(requestDto.endMatchingId(), scheduleDto));
    }
  }

  @Transactional(readOnly = true)
  public ManagerMatchingStatResponseDto getMatchingStat(Long memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    Manager manager = managerRepository.findOptionalByMember(member)
        .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));
    // 전체 매칭
    int matchCount = matchingRepository.countByManager(manager);

    // 신규 매칭 (기준: 2주 내)
    LocalDate now = LocalDate.now();
    LocalDate twoWeeksAgo = now.minusWeeks(2);
    int newMatchCount = matchingRepository.countByManagerAndDate(manager, twoWeeksAgo, now);

    // 달별 매칭
    Map<String, Integer> monthlyMatchCounts = new LinkedHashMap<>();
    for (int i = 0; i < 12; i++) {
      LocalDate monthStart = now.minusMonths(i).withDayOfMonth(1);
      int year = monthStart.getYear();
      int month = monthStart.getMonthValue();
      int count = matchingRepository.countByManagerAndMonth(manager, year, month);
      monthlyMatchCounts.put(monthStart.getMonth().toString() + " " + year, count);
    }

    // 매칭 중
    int progressMatchCount = matchingRepository.countByManagerAndMatchingStatus(manager, MatchingStatus.IN_PROGRESS);
    // 매칭 대기중
    int beforMatchCount = matchingRepository.countByManagerAndMatchingStatus(manager, MatchingStatus.BEFORE_REQUEST);
    // 매칭 완료
    int endMatchCount = endMatchingRepository.countByManager(manager);

    // 응답 대기
    int waitingMatchCount = matchingRepository.countByManagerAndRunningStatus(manager, RunningStatus.WAITING);
    // 조율 요청
    int pendingMatchCount = matchingRepository.countByManagerAndRunningStatus(manager, RunningStatus.PENDING);
    // 수락
    int acceptedMatchCount = matchingRepository.countByManagerAndRunningStatus(manager, RunningStatus.ACCEPTED);
    // 거절
    int rejectedMatchCount = matchingRepository.countByManagerAndRunningStatus(manager, RunningStatus.REJECTED);

    // 매칭 기록이 있는 어르신
    double avgMatchesPerSenior = matchingRepository.avgMatchesPerSenior(manager);

    return ManagerMatchingStatResponseDto.from(manager,
        matchCount,
        newMatchCount,
        monthlyMatchCounts,
        progressMatchCount,
        beforMatchCount,
        endMatchCount,
        waitingMatchCount,
        pendingMatchCount,
        acceptedMatchCount,
        rejectedMatchCount,
        avgMatchesPerSenior,
        (double) acceptedMatchCount / (acceptedMatchCount + rejectedMatchCount),
        (double) rejectedMatchCount / (acceptedMatchCount + rejectedMatchCount));
  }
}