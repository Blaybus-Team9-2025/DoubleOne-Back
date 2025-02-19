package org.doubleone.domain.chat.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.chat.dto.response.ChatMessageDto;
import org.doubleone.domain.chat.dto.response.ChatMessageListResponseDto;
import org.doubleone.domain.chat.dto.response.ChatMessageResponseDto;
import org.doubleone.domain.chat.entity.ChatMessage;
import org.doubleone.domain.chat.entity.ChatRoom;
import org.doubleone.domain.chat.repository.ChatMessageRepository;
import org.doubleone.domain.chat.repository.ChatRoomRepository;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.service.MemberService;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {
  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final MemberService memberService;

  public ChatMessageResponseDto saveMessage(ChatMessageDto messageDto) {
    chatRoomRepository.findById(messageDto.chatRoomId())
        .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
    ChatMessage chatMessage = messageDto.toEntity();
    chatMessageRepository.save(chatMessage);
    return ChatMessageResponseDto.from(chatMessage);
  }

  @Transactional(readOnly = true)
  public ChatMessageListResponseDto getMessageList(Long chatRoomId) {
    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
        .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));

    List<ChatMessage> messages = chatMessageRepository.findAllByChatRoomIdOrderByCreatedAtAsc(chatRoomId);
    List<ChatMessageResponseDto> messageList = messages.stream()
        .map(ChatMessageResponseDto::from)
        .collect(Collectors.toList());

    // 현재 로그인된 사용자
    Member currentMember = memberService.getCurrentMember();

    // 상대방 사용자 정보(name, profileImg) 가져오기
    String name;
    String profileImg;
    if (currentMember.equals(chatRoom.getManager().getMember())) {
      name = chatRoom.getWorker().getName();
      profileImg = chatRoom.getWorker().getProfileImg();
    } else {
      name = chatRoom.getManager().getName();
      profileImg = chatRoom.getManager().getProfileImg();
    }

    return new ChatMessageListResponseDto(name, profileImg, messageList);
  }


}
