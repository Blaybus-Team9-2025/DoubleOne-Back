package org.doubleone.domain.member.repository;

import java.util.Optional;
import org.doubleone.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

  Optional<Member> findByEmail(String email);
}
