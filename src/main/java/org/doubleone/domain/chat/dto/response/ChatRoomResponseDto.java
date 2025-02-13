package org.doubleone.domain.chat.dto.response;

import lombok.Builder;
import org.doubleone.domain.chat.entity.ChatRoom;

@Builder
public record ChatRoomResponseDto(
    Long chatRoomId,
    String title,
    Long workerId,
    Long managerId,
    boolean hasUnRead
) {

  public static ChatRoomResponseDto from(ChatRoom chatRoom, boolean hasUnRead){
    return ChatRoomResponseDto.builder()
        .chatRoomId(chatRoom.getChatRoomId())
        .title(chatRoom.getTitle())
        .workerId(chatRoom.getWorker().getWorkerId())
        .managerId(chatRoom.getManager().getManagerId())
        .hasUnRead(hasUnRead)
        .build();

  }

}
