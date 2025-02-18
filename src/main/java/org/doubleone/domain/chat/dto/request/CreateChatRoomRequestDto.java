package org.doubleone.domain.chat.dto.request;

import org.doubleone.domain.member.entity.MemberType;

public record CreateChatRoomRequestDto(
    String title,
    Long memberId // 채팅 상대의 memberId
) {

}
