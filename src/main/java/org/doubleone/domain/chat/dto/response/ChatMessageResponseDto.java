package org.doubleone.domain.chat.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import org.doubleone.domain.chat.entity.ChatMessage;
import org.doubleone.domain.chat.entity.MessageType;

@Builder
public record ChatMessageResponseDto(
    String chatId,
    Long chatRoomId,
    Long memberId,
    String content,
    MessageType messageType,
    LocalDateTime createdAt
){

  public static ChatMessageResponseDto from(ChatMessage chatMessage){
  return ChatMessageResponseDto.builder()
      .chatId(chatMessage.getChatId())
      .chatRoomId(chatMessage.getChatRoomId())
      .memberId(chatMessage.getMemberId())
      .content(chatMessage.getContent())
      .messageType(chatMessage.getMessageType())
      .createdAt(chatMessage.getCreatedAt())
      .build();
}
}