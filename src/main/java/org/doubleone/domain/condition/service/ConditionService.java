package org.doubleone.domain.condition.service;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.condition.dto.ConditionRequestDto;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.repository.ConditionRepository;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("해당 어르신이 존재하지 않습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("해당 근무 조건이 존재하지 않습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("해당 근무 조건이 존재하지 않습니다."));

        conditionRepository.delete(condition);
    }
}
