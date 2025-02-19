package org.doubleone.domain.chat.dto.response;

import java.util.List;
import lombok.Builder;
import org.doubleone.domain.member.entity.Member;

@Builder
public record ChatMessageListResponseDto(
    String name,
    String profileImg,
    List<ChatMessageResponseDto> chatMessageList
) {

  public static ChatMessageListResponseDto from(String name, String profileImg, List<ChatMessageResponseDto> chatMessageList){
    return ChatMessageListResponseDto.builder()
        .name(name)
        .profileImg(profileImg)
        .chatMessageList(chatMessageList)
        .build();
  }
}
