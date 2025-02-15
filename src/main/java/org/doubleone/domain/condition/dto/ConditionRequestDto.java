package org.doubleone.domain.condition.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.condition.entity.WorkType;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ConditionRequestDto {
    private Long seniorId;
    private int wage;
    private Map<String, List<String>> welfares;
    private WorkType workType;
    private Map<String, List<String>> services;
}
