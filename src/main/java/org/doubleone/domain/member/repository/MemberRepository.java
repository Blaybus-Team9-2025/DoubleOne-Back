package org.doubleone.domain.member.repository;

import org.doubleone.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MemberRepository extends JpaRepository<Member, Long>{

}
