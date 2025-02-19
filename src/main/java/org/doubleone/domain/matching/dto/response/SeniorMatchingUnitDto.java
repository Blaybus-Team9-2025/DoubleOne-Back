package org.doubleone.domain.matching.dto.response;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import lombok.Builder;
import org.doubleone.domain.senior.entity.Senior;

@Builder
public record SeniorMatchingUnitDto(
    Long seniorId,
    String name,
    String address,
    String profileImg,
    int age,
    List<WorkerMatchingUnitPerSeniorDto> workerMatchingList){

  public static SeniorMatchingUnitDto from(Senior senior, List<WorkerMatchingUnitPerSeniorDto> workerMatchingList) {
    return SeniorMatchingUnitDto.builder()
        .seniorId(senior.getSeniorId())
        .name(senior.getName())
        .address(senior.getAddress())
        .profileImg(senior.getProfileImg())
        .age(calculateAge(senior.getBirthDate()))
        .workerMatchingList(workerMatchingList)
        .build();
  }

  private static int calculateAge(LocalDate birthDate) {
    return Period.between(birthDate, LocalDate.now()).getYears();
  }
}
