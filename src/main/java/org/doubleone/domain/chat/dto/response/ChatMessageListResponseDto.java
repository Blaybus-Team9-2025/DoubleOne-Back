package org.doubleone.domain.chat.dto.response;

import java.util.List;

public record ChatMessageListResponseDto(
    List<ChatMessageResponseDto> chatMessageList
) {

}
