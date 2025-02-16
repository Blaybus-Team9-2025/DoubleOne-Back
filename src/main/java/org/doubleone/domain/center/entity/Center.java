package org.doubleone.domain.center.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "center")
@Getter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Center {
  @Id
  private String centerCode; // 장기요양기관코드

  private String centerName; // 장기요양기관이름
}

