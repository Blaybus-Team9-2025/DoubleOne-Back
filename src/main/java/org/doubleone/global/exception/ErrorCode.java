package org.doubleone.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {


  // Member
  MEMBER_NOT_FOUND(404, "멤버를 찾을 수 없습니다."),

  // Manager
  MANAGER_CANNOT_CREATE_CHAT(409, "관리자는 채팅을 시작할 수 없습니다."),

  // Chat
  CHATROOM_NOT_FOUND(404, "채팅방을 찾을 수 없습니다."),

  // Worker
  WORKER_NOT_FOUND(404, "요양보호사를 찾을 수 없습니다");

  private final int status;
  private final String message;
}
