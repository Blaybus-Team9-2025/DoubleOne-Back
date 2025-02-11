package org.doubleone.domain.region.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "region")
@Getter
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "region_id", updatable = false)
  private Long regionId;

  @Column(name = "city")
  @NotNull
  private String city;

  @Column(name = "district")
  @NotNull
  private String district;

  @Column(name = "neighborhood")
  @NotNull
  private String neighborhood;

}
