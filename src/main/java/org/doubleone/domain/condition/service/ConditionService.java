package org.doubleone.domain.condition.service;

import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.doubleone.domain.condition.dto.ConditionDetailsResponseDto;
import org.doubleone.domain.condition.dto.ConditionRequestDto;
import org.doubleone.domain.condition.dto.ConditionResponseDto;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.repository.ConditionRepository;
import org.doubleone.domain.endMatching.repository.EndMatchingRepository;
import org.doubleone.domain.manager.dto.SeniorConditionResponseDto;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.matching.repository.MatchingRepository;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.senior.dto.SeniorScheduleDto;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.domain.worker.dto.response.WorkerLicenseDto;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConditionService {

    private final ConditionRepository conditionRepository;
    private final SeniorRepository seniorRepository;
    private final ManagerRepository managerRepository;
    private final EndMatchingRepository endMatchingRepository;

    // 등록
    public Long createCondition(Long seniorId, ConditionRequestDto requestDto) {
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));

        Condition condition = conditionRepository.save(requestDto.toEntity(senior));

        return condition.getConditionId(); // 등록 후 conditionId 반환
    }


    // 수정
    public void updateCondition(Long seniorConditionId, ConditionRequestDto requestDto) {
        Condition condition = conditionRepository.findById(seniorConditionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_CONDITION_NOT_FOUND));

        condition.updateCondition(requestDto.title(), requestDto.amount(), requestDto.payType(),
                requestDto.wage(), requestDto.welfares(), requestDto.preferGender(), requestDto.workType(), requestDto.services());
    }

    // 삭제
    public void deleteCondition(Long conditionId) {
        conditionRepository.deleteById(conditionId);
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public ConditionDetailsResponseDto getConditionDetail(Long conditionId) {
        Condition condition = conditionRepository.findById(conditionId)
            .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_CONDITION_NOT_FOUND));
        List<ScheduleDto> schedules = condition.getSeniorSchedules().stream()
            .map(ScheduleDto::from)
            .toList();
        return ConditionDetailsResponseDto.from(condition, schedules);
    }

    // 목록 조회 (필터 추가)
    @Transactional(readOnly = true)
    public List<ConditionResponseDto> getConditionFilterList(Long managerId, String filter) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));

        List<Condition> conditions;

        if ("unmatched".equals(filter)) {
            conditions = conditionRepository.findUnmatchedConditions();
        } else {
            conditions = conditionRepository.findAllByOrderByCreatedAtDesc();
        }

        return conditions.stream()
            .filter(condition -> condition.getSenior().getManager().equals(manager))
            .map(ConditionResponseDto::from)
            .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<SeniorConditionResponseDto> getConditionList(Long managerId, String filter) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));

        List<Condition> conditions;

        if ("unmatched".equals(filter)) {
            conditions = conditionRepository.findUnmatchedConditions();
        } else {
            conditions = conditionRepository.findAllByOrderByCreatedAtDesc();
        }

        return conditions.stream()
            .filter(condition -> condition.getSenior().getManager().equals(manager))
            .map(condition -> {
                boolean isEnded = endMatchingRepository.existsByCondition(condition);
                return SeniorConditionResponseDto.from(condition, isEnded);
            })
            .sorted(Comparator
                .comparing(SeniorConditionResponseDto::isEndMatch)
                .thenComparing(SeniorConditionResponseDto::seniorConditionId, Comparator.reverseOrder())
            )
            .collect(Collectors.toList());
    }



}
