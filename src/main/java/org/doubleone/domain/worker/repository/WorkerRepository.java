package org.doubleone.domain.worker.repository;

import java.util.Optional;
import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.worker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

  Optional<Worker> findByMemberOpt(Member member);
  Worker findByMember(Member member);
}
