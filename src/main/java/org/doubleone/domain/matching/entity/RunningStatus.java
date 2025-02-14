package org.doubleone.domain.matching.entity;

public enum RunningStatus {
  BEFORE_REQUEST,
  WAITING, // 응답 대기
  PENDING, // 조율 요청
  ACCEPTED, // 수락
  REJECTED // 거절
}
