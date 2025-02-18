package org.doubleone.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {

  // Auth
  UNAUTHORIZED(401, "인증 정보가 누락되거나 잘못되었습니다."),
  ACCESS_DENIED(403, "접근 권한이 없습니다."),
  INVALID_SIGNATURE(401, "잘못된 JWT 서명입니다."),
  EXPIRED_ACCESS_TOKEN(401, "만료된 엑세스 토큰입니다."),
  INVALID_TOKEN(400, "잘못된 토큰입니다."),
  UNSUPPORTED_TOKEN(400, "지원하지 않는 토큰입니다."),
  EMPTY_CLAIMS(400, "JWT 클레임이 비어있습니다."),
  LOGIN_FAIL(401, "로그인에 실패하였습니다."),

  INVALID_REQUEST(400, "잘못된 요청입니다."),

  // Member
  MEMBER_NOT_FOUND(404, "멤버를 찾을 수 없습니다."),
  MEMBER_ALREADY_EXISTS(409, "해당 이메일을 가진 멤버가 존재합니다."),
  PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다."),

  // Manager
  MANAGER_CANNOT_CREATE_CHAT(409, "관리자는 채팅을 시작할 수 없습니다."),
  MANAGER_NOT_FOUND(404, "관리자를 찾을 수 없습니다."),

  // Worker
  WORKER_NOT_FOUND(404, "요양보호사를 찾을 수 없습니다."),
  WORKER_CONDITION_NOT_FOUND(404, "요양보호사의 희망근무조건을 찾을 수 없습니다."),

  // Chat
  CHATROOM_NOT_FOUND(404, "채팅방을 찾을 수 없습니다."),

  // Senior
  SENIOR_NOT_FOUND(404, "노인을 찾을 수 없습니다."),
  INVALID_SENIOR_REQUEST(400, "잘못된 노인 정보 요청입니다."),
  SENIOR_CONDITION_NOT_FOUND(404, "노인 근무 조건을 찾을 수 없습니다."),

  // Matching
  MATCHING_NOT_FOUND(400, "매칭을 찾을 수 없습니다."),
  END_MATCHING_NOT_FOUND(404, "수락된 매칭을 찾을 수 없습니다."),

  // Email
  INCORRECT_VERIFICATION_CODE(400, "인증번호가 일치하지 않습니다.").

  // s3
  FILE_UPLOAD_FAIL(500, "파일 업로드에 실패했습니다."), 
  INVALID_FILE_FORMAT(500, "파일 형식이 잘못되었습니다."),
  UNSUPPORTED_FILE_FORMAT(400, "지원하지 않는 파일 형식입니다."),

  ;

  private final int status;
  private final String message;
}
