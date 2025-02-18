package org.doubleone.domain.chat.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import org.doubleone.domain.chat.entity.ChatMessage;
import org.doubleone.domain.chat.entity.MessageType;

@Builder
public record ChatMessageDto(
    Long chatRoomId,
    Long memberId,
    String content,
    MessageType messageType
) {

  public ChatMessage toEntity(){
    return ChatMessage.builder()
        .chatRoomId(chatRoomId)
        .memberId(memberId)
        .content(content)
        .messageType(messageType)
        .createdAt(LocalDateTime.now())
        .build();
  }
}
