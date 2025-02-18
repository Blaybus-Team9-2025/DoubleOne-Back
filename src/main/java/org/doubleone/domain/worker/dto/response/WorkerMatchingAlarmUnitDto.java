package org.doubleone.domain.worker.dto.response;

import java.util.List;
import lombok.Builder;
import org.doubleone.domain.chat.entity.ChatRoom;
import org.doubleone.domain.matching.entity.Matching;
import org.doubleone.domain.schedule.dto.ScheduleDto;
import org.doubleone.domain.senior.dto.SeniorScheduleDto;

@Builder
public record WorkerMatchingAlarmUnitDto(
    Long matchingId,
    Long chatRoomId,
    List<ScheduleDto> seniorSchedules,
    String centerName,
    String address
) {

  public static WorkerMatchingAlarmUnitDto from(Matching matching, ChatRoom chatRoom, List<ScheduleDto> seniorSchedules){
    return WorkerMatchingAlarmUnitDto.builder()
        .matchingId(matching.getMatchingId())
        .chatRoomId(chatRoom.getChatRoomId())
        .seniorSchedules(seniorSchedules)
        .centerName(matching.getCondition().getSenior().getManager().getCenterName())
        .address(matching.getCondition().getSenior().getManager().getAddress())
        .build();
  }
}
