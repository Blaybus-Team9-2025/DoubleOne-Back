package org.doubleone.domain.chat.repository;

import java.util.List;
import org.doubleone.domain.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

  @Query(value = "{ 'chatRoomId': ?0 }", sort = "{ 'createdAt': -1 }")
  ChatMessage findFirstByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId);

  List<ChatMessage> findAllByChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);

  @Query(value = "{ 'chatRoomId': { $in: ?0 } }", sort = "{ 'createdAt': -1 }")
  List<ChatMessage> findLatestMessagesByChatRoomIds(List<Long> chatRoomIds);
}
