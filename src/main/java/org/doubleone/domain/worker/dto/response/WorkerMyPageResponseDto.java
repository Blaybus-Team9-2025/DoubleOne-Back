package org.doubleone.domain.worker.dto.response;

import java.util.List;
import lombok.Builder;
import org.doubleone.domain.worker.entity.Worker;

@Builder
public record WorkerMyPageResponseDto(
    String profileImg,
    String name,
    List<WorkerMatchingAlarmUnitDto> matchingAlarmlist
) {

  public static WorkerMyPageResponseDto from(Worker worker, List<WorkerMatchingAlarmUnitDto> matchingAlarmlist){
    return WorkerMyPageResponseDto.builder()
        .profileImg(worker.getProfileImg())
        .name(worker.getName())
        .matchingAlarmlist(matchingAlarmlist)
        .build();
  }
}
