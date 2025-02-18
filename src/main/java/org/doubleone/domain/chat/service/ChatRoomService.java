package org.doubleone.domain.chat.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.chat.dto.request.CreateChatRoomRequestDto;
import org.doubleone.domain.chat.dto.response.ChatRoomListResponseDto;
import org.doubleone.domain.chat.dto.response.ChatRoomResponseDto;
import org.doubleone.domain.chat.dto.response.ChatRoomUnitDto;
import org.doubleone.domain.chat.entity.ChatMessage;
import org.doubleone.domain.chat.entity.ChatRoom;
import org.doubleone.domain.chat.repository.ChatMessageRepository;
import org.doubleone.domain.chat.repository.ChatRoomRepository;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.entity.MemberType;
import org.doubleone.domain.member.service.MemberService;
import org.doubleone.domain.worker.entity.Worker;
import org.doubleone.domain.worker.repository.WorkerRepository;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {
  private final MemberService memberService;
  private final WorkerRepository workerRepository;
  private final ManagerRepository managerRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final ChatMessageRepository chatMessageRepository;

  public ChatRoomResponseDto createChatRoom(CreateChatRoomRequestDto requestDto) {
    Member member = memberService.getCurrentMember();
    Member otherMember = memberService.getMemberById(requestDto.memberId());

    if(member.getMemberType() != MemberType.WORKER){
      throw new CustomException(ErrorCode.MANAGER_CANNOT_CREATE_CHAT);
    }

    Worker worker = workerRepository.findByMember(member);
    Manager manager = managerRepository.findByMember(otherMember);

    // 기존 채팅방 존재하는지 확인
    Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByManagerAndWorker(manager, worker);
    if (existingChatRoom.isPresent()) {
      // 기존 채팅방 반환
      return ChatRoomResponseDto.from(existingChatRoom.get(), false);
    }

    ChatRoom chatRoom = ChatRoom.builder()
        .worker(worker)
        .manager(manager)
        .title(requestDto.title())
        .build();
    chatRoomRepository.save(chatRoom);
    return ChatRoomResponseDto.from(chatRoom, true);
  }

  @Transactional(readOnly = true)
  public ChatRoomListResponseDto getChatRoomList() {
    Member member = memberService.getCurrentMember();
    List<ChatRoom> chatRooms = (member.getMemberType() == MemberType.WORKER)
        ? chatRoomRepository.findByWorker(workerRepository.findByMember(member))
        : chatRoomRepository.findByManager(managerRepository.findByMember(member));

    List<Long> chatRoomIds = chatRooms.stream()
        .map(ChatRoom::getChatRoomId)
        .collect(Collectors.toList());

    List<ChatMessage> latestMessages = chatMessageRepository.findLatestMessagesByChatRoomIds(chatRoomIds);
    // Mag<ChatRoomId, latestMessage> 형태로 반환
    Map<Long, ChatMessage> latestMessageMap = latestMessages.stream()
        .collect(Collectors.toMap(ChatMessage::getChatRoomId, chatMessage -> chatMessage));

    List<ChatRoomUnitDto> chatRoomList = chatRooms.stream()
        .map(chatRoom -> ChatRoomUnitDto.from(chatRoom, latestMessageMap.get(chatRoom.getChatRoomId())))
        .collect(Collectors.toList());

    return new ChatRoomListResponseDto(chatRoomList);
  }

}
