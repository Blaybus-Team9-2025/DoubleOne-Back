package org.doubleone.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // Worker
  WORKER_NOT_FOUND(404, "요양보호사를 찾을 수 없습니다");

  private final int status;
  private final String message;
}
