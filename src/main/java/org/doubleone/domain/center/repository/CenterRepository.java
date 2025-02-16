package org.doubleone.domain.center.repository;


import java.util.List;
import org.doubleone.domain.center.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterRepository extends JpaRepository<Center, String> {
  List<Center> findByCenterNameContaining(String keyword);
}

