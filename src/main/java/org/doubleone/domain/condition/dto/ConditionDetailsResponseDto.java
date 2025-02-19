package org.doubleone.domain.condition.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.condition.entity.WorkType;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.worker.entity.Gender;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionDetailsResponseDto {

  private Long conditionId;
  private Long managerId;
  private String title;
  private int amount;
  private String payType;
  private int wage;
  private Map<String, List<String>> welfares;
  private WorkType workType;
  private Map<String, List<String>> services;
  Gender preferGender;
  List<ScheduleDto> seniorSchedules;

  public static ConditionDetailsResponseDto from(Condition condition, List<ScheduleDto> seniorSchedules) {
    return ConditionDetailsResponseDto.builder()
        .conditionId(condition.getConditionId())
        .managerId(condition.getSenior().getManager().getManagerId())
        .title(condition.getTitle())
        .amount(condition.getAmount())
        .payType(condition.getPayType().name())
        .wage(condition.getWage())
        .welfares(condition.getWelfares())
        .workType(condition.getWorkType())
        .services(condition.getServices())
        .preferGender(condition.getPreferGender())
        .seniorSchedules(seniorSchedules)
        .build();
  }
}
