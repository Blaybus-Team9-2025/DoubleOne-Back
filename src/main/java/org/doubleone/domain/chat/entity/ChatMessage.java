package org.doubleone.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "chatMessage")
public class ChatMessage {
  @Id
  @Field("_id")
  private String chatId; // MongoDB에서 랜덤한 ObjectId를 부여
  private Long chatRoomId;
  private Long memberId;
  private String content;
  private MessageType messageType;
  private LocalDateTime createdAt;
}
