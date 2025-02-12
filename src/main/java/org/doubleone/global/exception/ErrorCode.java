package org.doubleone.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // Member
  MEMBER_NOT_FOUND(404, "멤버를 찾을 수 없습니다."),


  // Chat
  CHATROOM_NOT_FOUND(404, "채팅방을 찾을 수 없습니다."),

  // Senior 관련 에러 코드 추가
  SENIOR_NOT_FOUND(404, "해당 노인을 찾을 수 없습니다."),
  INVALID_SENIOR_REQUEST(400, "잘못된 노인 정보 요청입니다.");

  private final int status;
  private final String message;
}
