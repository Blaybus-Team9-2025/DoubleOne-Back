package org.doubleone.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.chat.dto.request.CreateChatRoomRequestDto;
import org.doubleone.domain.chat.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Chat")
@RequiredArgsConstructor
@RequestMapping("/chatRooms")
public class ChatRoomController {

  private final ChatRoomService chatRoomService;

  @Operation(summary = "채팅방 생성", description = "요양사와 매니저 간의 1:1 채팅방을 생성")
  @PostMapping
  public ResponseEntity<?> createChatRoom(@RequestBody @Valid CreateChatRoomRequestDto requestDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomService.createChatRoom(requestDto));
  }

  @Operation(summary = "채팅방 목록 조회", description = "현재 사용자의 채팅방 목록을 조회")
  @GetMapping
  public ResponseEntity<?> getChatRoomList() {
    return ResponseEntity.ok(chatRoomService.getChatRoomList());
  }

}
