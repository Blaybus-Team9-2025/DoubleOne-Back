package org.doubleone.domain.matching.dto.response;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import lombok.Builder;
import org.doubleone.domain.condition.entity.Condition;
import org.doubleone.domain.senior.entity.Senior;

@Builder
public record SeniorMatchingUnitDto(
    Long seniorId,
    String name,
    String address,
    String profileImg,
    int age,
    List<WorkerMatchingUnitPerSeniorDto> workerMatchingList){

  public static SeniorMatchingUnitDto from(Condition condition, List<WorkerMatchingUnitPerSeniorDto> workerMatchingList) {
    return SeniorMatchingUnitDto.builder()
        .seniorId(condition.getSenior().getSeniorId())
        .name(condition.getSenior().getName())
        .address(condition.getSenior().getAddress())
        .profileImg(condition.getSenior().getProfileImg())
        .age(calculateAge(condition.getSenior().getBirthDate()))
        .workerMatchingList(workerMatchingList)
        .build();
  }

  private static int calculateAge(LocalDate birthDate) {
    return Period.between(birthDate, LocalDate.now()).getYears();
  }
}
