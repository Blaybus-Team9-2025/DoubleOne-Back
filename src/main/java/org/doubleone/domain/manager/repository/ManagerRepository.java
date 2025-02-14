package org.doubleone.domain.manager.repository;

import io.lettuce.core.Value;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

  Manager findByMember(Member member);
}
