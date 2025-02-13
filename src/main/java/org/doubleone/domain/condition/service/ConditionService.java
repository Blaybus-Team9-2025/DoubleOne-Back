package org.doubleone.domain.condition.service;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.condition.dto.ConditionRequestDto;
import org.doubleone.domain.condition.dto.ConditionResponseDto;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.repository.ConditionRepository;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ConditionService {

    private final ConditionRepository conditionRepository;
    private final SeniorRepository seniorRepository;

    // 등록
    public Long createCondition(ConditionRequestDto requestDto) {
        Senior senior = seniorRepository.findById(requestDto.getSeniorId())
                .orElseThrow(() -> new CustomException(ErrorCode.SENIOR_NOT_FOUND));

        Condition condition = Condition.createCondition(
                senior,
                requestDto.getWage(),
                requestDto.getWelfares(),
                requestDto.getServiceType(),
                requestDto.getServices()
        );

        conditionRepository.save(condition);
        return condition.getSeniorConditionId();
    }

    // 수정
    public void updateCondition(Long conditionId, ConditionRequestDto requestDto) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SENIOR_REQUEST));

        condition.updateCondition(
                requestDto.getWage(),
                requestDto.getWelfares(),
                requestDto.getServiceType(),
                requestDto.getServices()
        );
    }

    // 삭제
    public void deleteCondition(Long conditionId) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SENIOR_REQUEST));

        conditionRepository.delete(condition);
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public ConditionResponseDto getConditionDetail(Long conditionId) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SENIOR_REQUEST));
        return ConditionResponseDto.from(condition);
    }
}
