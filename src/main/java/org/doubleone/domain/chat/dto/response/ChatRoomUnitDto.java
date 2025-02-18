package org.doubleone.domain.chat.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import org.doubleone.domain.chat.entity.ChatMessage;
import org.doubleone.domain.chat.entity.ChatRoom;

@Builder
public record ChatRoomUnitDto(
    Long chatRoomId,
    String latestChat,
    LocalDateTime latestChatAt
) {

  public static ChatRoomUnitDto from(ChatRoom chatRoom, ChatMessage latestChat) {
    return ChatRoomUnitDto.builder()
        .chatRoomId(chatRoom.getChatRoomId())
        .latestChat(latestChat.getContent())
        .latestChatAt(latestChat.getCreatedAt())
        .build();
  }

}
