package org.doubleone.domain.chat.repository;

import java.util.List;
import java.util.Optional;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.worker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.doubleone.domain.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  Optional<ChatRoom> findByManagerAndWorker(Manager manager, Worker worker);

  List<ChatRoom> findByWorker(Worker worker);
  List<ChatRoom> findByManager(Manager manager);
}
