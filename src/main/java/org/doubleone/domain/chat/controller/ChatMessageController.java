package org.doubleone.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.chat.dto.response.ChatMessageDto;
import org.doubleone.domain.chat.entity.ChatMessage;
import org.doubleone.domain.chat.service.ChatMessageService;
import org.doubleone.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Chat")
@RequiredArgsConstructor
public class ChatMessageController {
  private final SimpMessageSendingOperations messagingTemplate;
  private final ChatMessageService chatMessageService;

  @Operation(summary = "채팅 생성", description = "채팅 메시지 생성")
  @MessageMapping("/chat/message") // 메시지 발행 경로: /pub/chat/message
  public void message(ChatMessageDto messageDto) {
    chatMessageService.saveMessage(messageDto);
    messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.chatRoomId(), messageDto);
    // 메시지 전송: /sub/chat/room/{chatRoomId}를 구독하는 사람에게
  }

  @Operation(summary = "채팅 메시지 목록 조회", description = "해당 채팅방의 메시지 목록을 조회")
  @GetMapping("/chat/{chatRoomId}")
  public ResponseEntity<?> getMessageList(@PathVariable("chatRoomId") Long chatRoomId) {
    return ResponseEntity.ok(chatMessageService.getMessageList(chatRoomId));
  }


}
