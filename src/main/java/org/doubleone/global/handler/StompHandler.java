package org.doubleone.global.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.doubleone.global.jwt.TokenProvider;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

  private final TokenProvider tokenProvider;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    // websocket 연결, 채팅 메시지 전송 시 토큰 검증
    if (accessor.getCommand() == StompCommand.CONNECT
        || accessor.getCommand() == StompCommand.SEND) {
      String accessToken = accessor.getFirstNativeHeader("Authorization");

      if (accessToken == null || !accessToken.startsWith("Bearer ")) {
        throw new CustomException(ErrorCode.ACCESS_DENIED);
      }

      accessToken = accessToken.substring(7);
      tokenProvider.isValidToken(accessToken);

//      Long userId = jwtService.extractUserId(accessToken);
//
//      if (accessor.getCommand() == StompCommand.SEND) {
//        validateMessageUserId(message, userId);
//      }
    }
    return message;
  }

//  // 채팅 메시지의 memberId와 토큰 memberId 일치 여부 확인
//  private void validateMessageUserId(Message<?> message, Long userId) {
//    try {
//      String payload = new String((byte[]) message.getPayload());
//      ObjectMapper objectMapper = new ObjectMapper();
//      JsonNode jsonNode = objectMapper.readTree(payload);
//
//      Long messageUserId = jsonNode.get("userId").asLong();
//
//      if (!userId.equals(messageUserId)) {
//        log.error("Unauthorized: 토큰의 userId와 메시지 송신자의 userId가 일치하지 않습니다.");
//        throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
//      }
//    } catch (Exception e) {
//      log.error("메시지 검증 중 오류 발생: {}", e.getMessage());
//      throw new CustomException(ErrorCode.INVALID_MESSAGE_PAYLOAD);
//    }
//  }
}
