package org.doubleone.domain.manager.repository;

import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByMemberOpt(Member member);
    Manager findByMember(Member member);
}
